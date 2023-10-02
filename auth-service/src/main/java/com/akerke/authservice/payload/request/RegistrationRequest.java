package com.akerke.authservice.payload.request;

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

        @Pattern(
                regexp = "^\\d{3}-\\d{3}-\\d{4}$",
                message = "Invalid phone number format. Use ###-###-####." // todo fix regexp
        )
        String phone,
        @Size(min = 8)
        String password
) {
}

