package com.akerke.authservice.domain.payload.request;

import com.akerke.authservice.common.annotations.Password;
import com.akerke.authservice.common.annotations.PhoneNumber;
import jakarta.validation.constraints.*;

/**
 * A data transfer object (DTO) used for registration information.
 * It represents user registration details such as name, surname, email, and phone number.
 */
public record RegistrationRequest(
        @NotBlank
        @NotNull
        String name,
        @NotBlank
        @NotNull
        String surname,
        @Email
        String email,
        @PhoneNumber
        String phone,
        @Password
        String password
) {
}

