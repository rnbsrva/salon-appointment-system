package com.akerke.authservice.common.utils;

import com.akerke.authservice.common.constants.TokenType;

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
