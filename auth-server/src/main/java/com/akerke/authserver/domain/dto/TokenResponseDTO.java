package com.akerke.authserver.domain.dto;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken,
        Long accessTokenExpiration,
        Long refreshTokenExpiration,
        String type
) {
    public TokenResponseDTO(
            String accessToken,
            String refreshToken,
            Long accessTokenExpiration,
            Long refreshTokenExpiration
    ) {
        this(
                accessToken,
                refreshToken,
                accessTokenExpiration,
                refreshTokenExpiration,
                "Bearer "
        );
    }

}
