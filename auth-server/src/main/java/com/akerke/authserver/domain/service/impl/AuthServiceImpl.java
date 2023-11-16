package com.akerke.authserver.domain.service.impl;

import com.akerke.authserver.common.constants.TokenType;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwt;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
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

        return createTokenResponse(user);
    }


    @Override
    public User register(
            RegistrationDTO registration
    ) {
        var optionalEmailUser = userRepository.findByEmail(registration.email());
        if (optionalEmailUser.isPresent()) {
            throw new EmailRegisteredYetException(registration.email());
        }

        var optionalPhoneUser = userRepository.findByPhoneNumber(registration.phone());
        if (optionalPhoneUser.isPresent()) {
            throw new PhoneNumberRegisteredYetException(registration.phone());
        }
        var otp = random.nextInt(100_000, 999_999);
        kafkaTemplate.send("email_confirmation",
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

        return createTokenResponse(user);
    }

    private TokenResponseDTO createTokenResponse(User user) {
        return new TokenResponseDTO(
                jwt.createToken(user, TokenType.ACCESS),
                jwt.createToken(user, TokenType.REFRESH),
                accessTokenExpiration,
                refreshTokenExpiration
        );
    }
}
