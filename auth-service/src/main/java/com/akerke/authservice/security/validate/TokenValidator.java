package com.akerke.authservice.security.validate;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.payload.response.StatusResponse;
import io.jsonwebtoken.Claims;

public interface TokenValidator {

    String TOKEN_TYPE_CLAIM_KEY = "token_type";

    StatusResponse validate(String token);

    TokenType getType();

    static Boolean sameTokenClaims(Claims claims, TokenType type) {
        return claims.containsKey(TOKEN_TYPE_CLAIM_KEY) && type == TokenType.valueOf(claims.get(TOKEN_TYPE_CLAIM_KEY).toString());
    }

}
