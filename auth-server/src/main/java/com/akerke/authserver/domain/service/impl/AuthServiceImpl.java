package com.akerke.authserver.domain.service.impl;

import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.common.jwt.JwtService;
import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.OTP;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.dto.TokenResponseDTO;
import com.akerke.authserver.domain.mapper.UserMapper;
import com.akerke.authserver.domain.model.User;
import com.akerke.authserver.domain.repository.ReactiveUserRepository;
import com.akerke.authserver.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.akerke.authserver.common.exception.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final ReactiveUserRepository reactiveUserRepository;
    private final JwtService jwt;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducer;
    private final Random random = new Random();

    @Value("${app.otp.expiration}")
    private Integer otpExpiration;

    @Value("${jwt.token.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.token.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Override
    public Mono<TokenResponseDTO> authenticate(
            AuthDTO auth
    ) {
        return reactiveUserRepository.findByEmail(
                auth.email()
        ).flatMap(user -> {
            if (user == null || !passwordEncoder.matches(auth.password(), user.getPassword())) {
                return Mono.error(InvalidCredentialsException::new);
            }
            if (!user.getEmailConfirmed()) {
                return Mono.error(EmailNotVerified::new);
            }
            return Mono.just(
                    new TokenResponseDTO(
                            jwt.createToken(user, TokenType.ACCESS),
                            jwt.createToken(user, TokenType.REFRESH),
                            accessTokenExpiration,
                            refreshTokenExpiration
                    )
            );
        });
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(RegistrationDTO.class)
                .flatMap(registration ->
                        reactiveUserRepository.findByEmail(registration.email())
                                .flatMap(optionalUser1 -> {
                                    if (optionalUser1 != null) {
                                        log.error("Email already registered: {}", registration.email());
                                        return Mono.error(new EmailRegisteredYetException(registration.email()));
                                    }

                                    return reactiveUserRepository.findByPhoneNumber(registration.phone())
                                            .flatMap(optionalUser2 -> {
                                                if (optionalUser2 != null) {
                                                    log.error("Phone already registered: {}", registration.phone());
                                                    return Mono.error(new PhoneNumberRegisteredYetException(registration.phone()));
                                                }

                                                var otp = random.nextInt(100_000, 999_999);
                                                return reactiveKafkaProducer.send(
                                                        "email_confirmation",
                                                        Map.of(
                                                                "recipient", registration.email(),
                                                                "msgBody", String.valueOf(otp) // Convert to String
                                                        )
                                                ).doOnNext(sr -> {
                                                    log.info("Email confirmation sent: {}", registration.email());
                                                }).then(
                                                        reactiveUserRepository.save(userMapper.toModel(registration))
                                                );
                                            });
                                })
                                .flatMap(user -> ServerResponse.status(201).bodyValue(user))
                )
                .doOnError(throwable -> {
                    log.error("Error in registration", throwable);
                    // Handle the error or rethrow it based on your requirements
                })
                .doFinally(signalType -> {
                    log.info("Registration completed." + signalType.name());
                });
    }



    @Override
    public Mono<User> register(
            RegistrationDTO registration
    ) {
        return reactiveUserRepository
                .findByEmail(registration.email())
                .flatMap(optionalUser1 -> {
                    if (optionalUser1 != null) {
                        log.error("email registered yet " + registration.email());
                        return Mono.error(new EmailRegisteredYetException(registration.email()));
                    }
                    return reactiveUserRepository
                            .findByPhoneNumber(registration.phone())
                            .flatMap(optionalUser2 -> {
                                log.error("phone registered yet " + registration.email());
                                if (optionalUser2 != null) {
                                    return Mono.error(new PhoneNumberRegisteredYetException(registration.phone()));
                                }
                                var otp = random.nextInt(100_000, 999_999);
                                reactiveKafkaProducer.send(
                                                "email_confirmation",
                                                Map.of(
                                                        "recipient", registration.email(),
                                                        "msgBody", otp
                                                )
                                        ).doOnSuccess(sr -> {
                                        })
                                        .subscribe((r) -> log.info("{}", r));

                                return reactiveUserRepository.save(userMapper.toModel(registration));
                            });
                })
                .doOnError(throwable -> log.error("Error in registration", throwable));
    }

    @Override
    public Mono<TokenResponseDTO> confirmEmail(OTP otp) {
        return reactiveUserRepository
                .findByEmail(otp.email())
                .flatMap(user -> {
                    if (user == null) {
                        return Mono.error(InvalidCredentialsException::new);
                    } else if (user.getOtpCreationTime().isBefore(LocalDateTime.now().minusSeconds(otpExpiration))) {
                        return Mono.error(OTPExpiredException::new);
                    } else if (!user.getOtp().equals(otp.otp())) {
                        return Mono.error(InvalidOTPException::new);
                    } else {
                        return Mono.just(
                                new TokenResponseDTO(
                                        jwt.createToken(user, TokenType.ACCESS),
                                        jwt.createToken(user, TokenType.REFRESH),
                                        accessTokenExpiration,
                                        refreshTokenExpiration
                                )
                        );
                    }
                });
    }
}
