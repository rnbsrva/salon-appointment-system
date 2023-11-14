package com.akerke.authserver.domain.service.impl;

import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.common.jwt.JwtService;
import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.TokenResponseDTO;
import com.akerke.authserver.domain.repository.ReactiveUserRepository;
import com.akerke.authserver.domain.service.AuthService;
import com.akerke.exceptionlib.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ReactiveUserRepository reactiveUserRepository;
    private final JwtService jwt;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.token.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Override
    public Mono<TokenResponseDTO> authenticate(
            AuthDTO auth
    ) {
        return reactiveUserRepository.findByEmail(
                Mono.just(auth.email())
        ).flatMap(user -> {
            if (user == null || !passwordEncoder.matches(auth.password(), user.getPassword())) {
                return Mono.error(InvalidCredentialsException::new);
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
}
