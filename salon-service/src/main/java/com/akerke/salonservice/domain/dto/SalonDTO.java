package com.akerke.salonservice.domain.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalonDTO(
        @NotNull
        Long ownerId,
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank @Email
        String email,
        @NotNull
        AddressDTO addressDTO,
        String description
) {
}
