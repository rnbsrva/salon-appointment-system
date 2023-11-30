package com.akerke.salonservice.common.payload;

public record SalonSearch (
        String name,
        String street,
        String city,
        String state,
        String treatmentName
) {
}
