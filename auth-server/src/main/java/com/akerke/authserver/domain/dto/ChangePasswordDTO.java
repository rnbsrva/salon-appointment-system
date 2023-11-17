package com.akerke.authserver.domain.dto;

public record ChangePasswordDTO(
        String email,
        String oldPassword,
        String newPassword
) {
}
