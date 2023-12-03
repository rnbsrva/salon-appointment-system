package com.akerke.salonservice.domain.dto;

import com.akerke.salonservice.common.constants.Gender;
import jakarta.validation.constraints.*;

public record UserDTO (
     @NotBlank @Size(min = 2, max = 255)
     String name,
     @NotBlank @Size(min = 2, max = 255)
     String surname,
     @NotBlank @Size(min = 9, max = 25)
     String phone,
     Gender gender,
     @NotBlank @Email
     String email
) {
}
