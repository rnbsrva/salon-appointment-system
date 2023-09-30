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
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.akerke.authservice.reflection.MapUtils.toMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwt;
    private final UserService userService;
//    private final KafkaTemplate<String, Object> kafka;

    public static final String TOKEN_TYPE_CLAIM_KEY = "token_type";

    @Override
    public Boolean validateToken(String token, TokenType type) {
        return null;
    }

    @Override
    public TokenResponse token(User user) {
        var accessTokenClaims = toMap(user, new Pair(TOKEN_TYPE_CLAIM_KEY, TokenType.ACCESS_TOKEN.getName()));
        var refreshTokenClaims = toMap(user, new Pair(TOKEN_TYPE_CLAIM_KEY, TokenType.REFRESH_TOKEN.getName()));

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

    @Override
    public StatusResponse confirmEmail() {
        return null; // if email confirmed => add scope email to user
    }


}
