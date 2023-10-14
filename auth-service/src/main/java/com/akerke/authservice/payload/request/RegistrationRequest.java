package com.akerke.authservice.payload.request;

import com.akerke.authservice.annotations.Password;
import com.akerke.authservice.annotations.PhoneNumber;
import com.akerke.authservice.constants.Gender;
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
        String password,
        @NotNull
        Gender gender
) {
}

