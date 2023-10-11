package com.akerke.authservice.security.validate;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;
import com.akerke.authservice.repository.UserRepository;
import com.akerke.authservice.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class TokenValidator {

    public final static String TOKEN_TYPE_CLAIM_KEY = "token_type";

    protected final JwtUtil jwt;
    protected final UserRepository userRepository;

    abstract TokenType getType();

    abstract StatusResponse validate(String token);

    public StatusResponse validate(String token, TokenType type) {
        Claims claims;
        try {
            claims = jwt.extractAllClaims(token);
        } catch (Exception e) {
            log.error("exception during extract claims: {} ", e.getMessage());
            return StatusResponse.fail("invalid credentials");
        }

        if (sameTokenClaims(claims, type)) {
            return StatusResponse.fail("invalid token claims");
        }

        var email = claims.getSubject();
        var optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.info("valid token, but user from db not found");
            return StatusResponse.fail("invalid credentials");
        }

        var user = optionalUser.get();

        log.info("success authentication, email: {}, id: {}", user.getEmail(), user.getId());

        return StatusResponse.success(user);
    }

    static Boolean sameTokenClaims(Claims claims, TokenType type) {
        return claims.containsKey(TOKEN_TYPE_CLAIM_KEY) && type == TokenType.valueOf(claims.get(TOKEN_TYPE_CLAIM_KEY).toString());
    }

}
