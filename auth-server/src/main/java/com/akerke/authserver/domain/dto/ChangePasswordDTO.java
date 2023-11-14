package com.akerke.authserver.domain.dto;

import com.akerke.authserver.common.annotations.Password;
import jakarta.validation.constraints.Email;

public record ChangePasswordDTO(
        @Email
        String email,
        String oldPassword,
        @Password
        String newPassword,
        String newPasswordConfirmation
) {
}
