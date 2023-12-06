package com.akerke.authserver.domain.dto;


public record AuthDTO(
        String email,
        String password
) {
}
