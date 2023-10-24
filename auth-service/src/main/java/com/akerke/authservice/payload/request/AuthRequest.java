package com.akerke.authservice.payload.request;

import com.akerke.authservice.common.annotations.Password;
import jakarta.validation.constraints.Email;

public record AuthRequest(
        @Email
        String email,
        @Password
        String password
) {
}
