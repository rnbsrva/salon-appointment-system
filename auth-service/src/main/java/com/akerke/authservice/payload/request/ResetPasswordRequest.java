package com.akerke.authservice.payload.request;

import jakarta.validation.constraints.Email;

public record ResetPasswordRequest(
        @Email
        String email,
        String oldPassword,
        String newPassword
) {
}
