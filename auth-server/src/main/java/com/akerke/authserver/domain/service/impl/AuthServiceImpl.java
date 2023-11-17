package com.akerke.authserver.domain.service.impl;

import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.domain.model.Token;
import com.akerke.authserver.infrastructure.redis.RedisTokenService;
import com.akerke.authserver.common.exception.*;
import com.akerke.authserver.common.jwt.JwtService;
import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.OTP;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.dto.TokenResponseDTO;
import com.akerke.authserver.domain.mapper.UserMapper;
import com.akerke.authserver.domain.model.User;
import com.akerke.authserver.domain.repository.UserRepository;
import com.akerke.authserver.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.metrics.stats.TokenBucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import static com.akerke.authserver.common.jwt.JwtService.TOKEN_CLAIM_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwt;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RedisTokenService redisTokenService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Random random = new Random();

    @Value("${app.otp.expiration}")
    private Integer otpExpiration;

    @Value("${jwt.token.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.token.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Override
    public TokenResponseDTO authenticate(
            AuthDTO auth
    ) {
        var optionalUser = userRepository.findByEmail(auth.email());
        if (optionalUser.isEmpty() || passwordEncoder.matches(auth.password(), optionalUser.get().getPassword())) {
            throw new InvalidCredentialsException();
        }

        var user = optionalUser.get();

        if (!user.getEmailConfirmed()) {
            throw new EmailNotVerified();
        }

        return createTokenResponseAndCacheTokens(user);
    }


    @Override
    public User register(
            RegistrationDTO registration
    ) {
        var optionalEmailUser = userRepository.findByEmail(registration.email());
        if (optionalEmailUser.isPresent()) {
            throw new EmailRegisteredYetException(registration.email());
        }

        var optionalPhoneUser = userRepository.findByPhoneNumber(registration.phoneNumber());
        if (optionalPhoneUser.isPresent()) {
            throw new PhoneNumberRegisteredYetException(registration.phoneNumber());
        }
        var otp = random.nextInt(100_000, 999_999);
        kafkaTemplate.send("email_verification",
                Map.of(
                        "recipient", registration.email(),
                        "msgBody", otp
                ));

        return userRepository.save(userMapper.toModel(registration, otp));
    }

    @Override
    public TokenResponseDTO confirmEmail(OTP otp) {
        var optionalUser = userRepository.findByEmail(otp.email());

        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        var user = optionalUser.get();

        if (user.getOtpCreationTime().isBefore(LocalDateTime.now().minusSeconds(otpExpiration))) {
            throw new OTPExpiredException();
        }

        if (!user.getOtp().equals(otp.otp())) {
            throw new InvalidOTPException();
        }

        return createTokenResponseAndCacheTokens(user);
    }

    @Override
    public TokenResponseDTO refreshToken(
            String refreshToken
    ) {
        User user = null;
        try {
            var decodedJWT = jwt.convertToken(refreshToken);
            var tokenType = TokenType.valueOf(decodedJWT.getClaim(TOKEN_CLAIM_KEY).asString());

            if (tokenType != TokenType.REFRESH) {
                throw new InvalidTokenTypeException();
            }

            var email = decodedJWT.getSubject();
            var optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isEmpty()) {
                throw new InvalidCredentialsException();
            }

            user = optionalUser.get();
            return createTokenResponseAndCacheTokens(user);
        } catch (Exception e) {
            var tokens = redisTokenService.get(user.getEmail());
            if (tokens.stream().anyMatch(token -> token.getType() == TokenType.REFRESH)) {
                return createTokenResponseAndCacheTokens(user);
            }
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public void logout(String email) {

    }

    private TokenResponseDTO createTokenResponseAndCacheTokens(User user) {
        var accessToken = jwt.createToken(user, TokenType.ACCESS);
        var refreshToken = jwt.createToken(user, TokenType.REFRESH);

        redisTokenService.save(accessToken);
        redisTokenService.save(refreshToken);

        return new TokenResponseDTO(
                accessToken.getValue(),
                refreshToken.getValue(),
                accessTokenExpiration,
                refreshTokenExpiration
        );
    }


}
