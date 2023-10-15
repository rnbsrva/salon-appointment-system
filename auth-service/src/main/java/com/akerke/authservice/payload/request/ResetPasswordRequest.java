package com.akerke.authservice.payload.request;

import com.akerke.authservice.annotations.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @Email
        String email,
        @JsonProperty("old_password")
        String oldPassword,
        @Password
        @JsonProperty("new_password")
        String newPassword,
        @JsonProperty("new_password_confirmation")
        String newPasswordConfirmation
) {
}
