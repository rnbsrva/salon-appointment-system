package com.akerke.authserver.domain.service.impl;

import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.domain.dto.*;
import com.akerke.authserver.domain.model.Token;
import com.akerke.authserver.infrastructure.redis.RedisTokenService;
import com.akerke.authserver.common.exception.*;
import com.akerke.authserver.common.jwt.JwtService;
import com.akerke.authserver.domain.mapper.UserMapper;
import com.akerke.authserver.domain.model.User;
import com.akerke.authserver.domain.repository.UserRepository;
import com.akerke.authserver.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.metrics.stats.TokenBucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

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
        var optionalUser = userRepository.findUserByEmail(auth.email());

        if (optionalUser.isEmpty() || passwordEncoder.matches( optionalUser.get().getPassword(),auth.password())) {
            throw new InvalidCredentialsException();
        }

        var user = optionalUser.get();

        if (!user.getEmailConfirmed()) {
            throw new EmailNotVerified();
        }

        log.info("authenticated user {}", auth.email());

        return createTokenResponseAndCacheTokens(user);
    }


    @Override
    public User register(
            RegistrationDTO registration
    ) {
        var optionalEmailUser = userRepository.findUserByEmail(registration.email());
        if (optionalEmailUser.isPresent()) {
            throw new EmailRegisteredYetException(registration.email());
        }

        var optionalPhoneUser = userRepository.findByPhoneNumber(registration.phoneNumber());
        if (optionalPhoneUser.isPresent()) {
            throw new PhoneNumberRegisteredYetException(registration.phoneNumber());
        }
        var otp = randomOtp.get();

        sendVerificationDetails(registration.email(), otp);
        log.info("new user register");

        var user = userMapper.toModel(registration, otp);
        user.setPassword(passwordEncoder.encode(registration.password()));
        return userRepository.save(user);
    }

    @Override
    public TokenResponseDTO confirmEmail(OTP otp) {
        var optionalUser = userRepository.findUserByEmail(otp.email());

        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        var user = optionalUser.get();

        var time = LocalDateTime.now();

        if (user.getOtpCreationTime().isBefore(time.minusSeconds(otpExpiration))) {
            throw new OTPExpiredException();
        }

        if (!user.getOtp().equals(otp.otp())) {
            throw new InvalidOTPException();
        }
        user.setEmailConfirmed(true);
        userRepository.save(user);

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
            var optionalUser = userRepository.findUserByEmail(email);

            if (optionalUser.isEmpty()) {
                throw new InvalidCredentialsException();
            }

            user = optionalUser.get();
            return createTokenResponseAndCacheTokens(user);
        } catch (Exception e) {
            var tokens = redisTokenService.get(user.getEmail(), TokenType.REFRESH);
            if (tokens.stream().anyMatch(token -> token.getType() == TokenType.REFRESH)) {
                return createTokenResponseAndCacheTokens(user);
            }
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public void logout(String email) {
        redisTokenService.clear(email);
    }

    @Override
    public void resendEmail(String email) {
        log.info("resend new email message {}", email);
        var otp = randomOtp.get();
        sendVerificationDetails(email, otp);
    }

    @Override
    public StatusResponseDTO changePassword(
            ChangePasswordDTO changePassword
    ) {
        var user = userRepository.findUserByEmail(changePassword.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(changePassword.oldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        user.setPassword(passwordEncoder.encode(changePassword.newPassword()));
        userRepository.save(user);

        return new StatusResponseDTO(true);
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

    private void sendVerificationDetails(String email, int otp) {
        kafkaTemplate.send("email_verification",
                Map.of(
                        "recipient", email,
                        "msgBody", otp
                )).addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Message sent successfully to Kafka. Topic: {}, Partition: {}, Offset: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending message to Kafka: {}", ex.getMessage(), ex);
            }
        });
        log.info("sent to kafka ");

    }

    private final Supplier<Integer> randomOtp = () -> random.nextInt(100_000, 999_999);

}
