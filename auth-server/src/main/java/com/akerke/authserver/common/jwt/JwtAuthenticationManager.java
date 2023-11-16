package com.akerke.authserver.common.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.akerke.authserver.common.jwt.JwtService.ROLES_CLAIM_KEY;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(
            Authentication authentication
    ) {
        return Mono.just(authentication)
                .map(auth -> jwtService.convertToken(auth.getCredentials().toString()))
                .onErrorResume(Mono::error)
                .map(decodedJWT -> {
                    return new UsernamePasswordAuthenticationToken(
                            decodedJWT.getSubject(),
                            authentication.getCredentials(),
                            decodedJWT.getClaim(ROLES_CLAIM_KEY).asList(String.class)
                                    .stream().map(SimpleGrantedAuthority::new).toList()
                    );
                });
    }
}
