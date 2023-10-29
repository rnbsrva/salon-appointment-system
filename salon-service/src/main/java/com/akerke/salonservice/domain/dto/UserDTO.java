package com.akerke.salonservice.domain.dto;

import com.akerke.salonservice.common.constants.Gender;
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
