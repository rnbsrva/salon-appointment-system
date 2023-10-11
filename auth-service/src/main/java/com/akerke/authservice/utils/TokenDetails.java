package com.akerke.authservice.utils;

import com.akerke.authservice.constants.TokenType;

public record TokenDetails(
        Integer expiration,
        String value
) {
    public TokenDetails(TokenType type, String token) {
        this(
                type.getExpirationMinute() * 60, token
        );
    }
}
