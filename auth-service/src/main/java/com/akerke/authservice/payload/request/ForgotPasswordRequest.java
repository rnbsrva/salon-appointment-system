package com.akerke.authservice.payload.request;

import com.akerke.authservice.annotations.Password;

public record ForgotPasswordRequest(
        @Password
        String newPassword,
        String newPasswordConfirmation
) {
}
