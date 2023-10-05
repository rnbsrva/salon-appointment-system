package com.akerke.authservice.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @Email
        String email,
        String oldPassword,
        @Size(min = 8)
        String newPassword
) {
}
