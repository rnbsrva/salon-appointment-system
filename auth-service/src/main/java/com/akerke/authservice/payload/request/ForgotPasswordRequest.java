package com.akerke.authservice.payload.request;

import com.akerke.authservice.common.annotations.Password;

public record ForgotPasswordRequest(
        @Password
        String newPassword,
        String newPasswordConfirmation
) {
}
