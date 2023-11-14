package com.akerke.authserver.domain.dto;

import com.akerke.authserver.common.annotations.Password;
import jakarta.validation.constraints.Email;

public record AuthDTO(
//        @Email
        String email,
//        @Password
        String password
) {
}
