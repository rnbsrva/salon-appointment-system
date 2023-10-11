package com.akerke.authservice.payload.request;

import com.akerke.authservice.annotations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @Email
        String email,
        String oldPassword,
        @Password
        String newPassword,
        String newPasswordConfirmation
) {
}
