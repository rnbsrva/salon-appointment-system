package com.akerke.salonservice.dto;


public record SalonDTO(
        Long ownerId,
        String name,
        String phone,
        String email,
        AddressDTO addressDTO,
        String description
) {
}
