package com.akerke.authservice.service.impl;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;
import com.akerke.authservice.security.JwtUtil;
import com.akerke.authservice.service.AuthService;
import com.akerke.authservice.service.UserService;
import com.akerke.authservice.utils.Pair;
import com.akerke.authservice.utils.TokenDetails;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.akerke.authservice.reflection.MapUtils.toMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwt;
    private final UserService userService;

    public static final String TOKEN_TYPE_CLAIM_KEY = "token_type";

    @Override
    public StatusResponse validateToken(String token, TokenType type) {
        try {
            var claims = jwt.extractAllClaims(token);
            var sameTokenClaim = sameTokenClaims(claims, type);

            if (!sameTokenClaim) {
                return new StatusResponse(false, "invalid claims");
            }

            var email = claims.getSubject();
            var user = userService.findByEmail(email);

            switch (type) {
                case ACCESS_TOKEN -> {
                    return new StatusResponse(true, user);
                }
                case REFRESH_TOKEN -> {
                    return new StatusResponse(true, token(user));
                }
                case VERIFICATION_TOKEN -> {
                    userService.verifyEmail(user);
                    return new StatusResponse(true, "email verified");
                }
                default -> {
                    throw new IllegalArgumentException();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StatusResponse(
                    false, e.getMessage()
            );
        }
    }

    @Override
    public TokenResponse token(User user) {
        var accessTokenClaims = toMap(user, new Pair(TOKEN_TYPE_CLAIM_KEY, TokenType.ACCESS_TOKEN));
        var refreshTokenClaims = toMap(user, new Pair(TOKEN_TYPE_CLAIM_KEY, TokenType.REFRESH_TOKEN));

        return new TokenResponse(
                new TokenDetails(
                        TokenType.ACCESS_TOKEN,
                        jwt.generateToken(TokenType.ACCESS_TOKEN, accessTokenClaims, user.getEmail())
                ),
                new TokenDetails(
                        TokenType.REFRESH_TOKEN,
                        jwt.generateToken(TokenType.REFRESH_TOKEN, refreshTokenClaims, user.getEmail())
                ),
                user
        );
    }

    private Boolean sameTokenClaims(Claims claims, TokenType type) {
        return claims.containsKey(TOKEN_TYPE_CLAIM_KEY) && type == TokenType.valueOf(claims.get(TOKEN_TYPE_CLAIM_KEY).toString());
    }


}
