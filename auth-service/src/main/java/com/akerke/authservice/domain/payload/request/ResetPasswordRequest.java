package com.akerke.authservice.domain.payload.request;

import com.akerke.authservice.common.annotations.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

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
