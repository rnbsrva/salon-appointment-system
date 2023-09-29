package com.akerke.salonservice.dto;

public record SalonDTO(
        String name,
        String phone,
        String email,
        String address,
        String description
) {
}
