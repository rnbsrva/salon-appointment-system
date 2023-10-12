package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.Gender;
import jakarta.validation.constraints.NotBlank;

public record UserDTO (
     @NotBlank
     String name,
     String surname,
     @NotBlank
     String phone,
     Gender gender,
     @NotBlank
     String email
) {
}
