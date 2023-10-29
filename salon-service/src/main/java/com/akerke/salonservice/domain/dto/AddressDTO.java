package com.akerke.salonservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressDTO(
        @NotNull
         Long houseNumber,
        @NotBlank @Size(min=2, max = 255)
         String street,
        @NotBlank @Size(min=2, max = 255)
         String city,
        @NotBlank @Size(min=2, max = 255)
         String state
) {
}
