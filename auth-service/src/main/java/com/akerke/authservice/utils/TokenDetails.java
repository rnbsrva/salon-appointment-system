package com.akerke.authservice.utils;

import com.akerke.authservice.constants.TokenType;

public record TokenDetails(
        Integer expiration,
        String token
) {
    public TokenDetails(TokenType type, String token) {
        this(
                type.getExpirationMinute() * 60, token
        );
    }
}
