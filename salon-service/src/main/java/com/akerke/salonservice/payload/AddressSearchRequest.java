package com.akerke.salonservice.payload;

public record AddressSearchRequest(
        String state,
        String city,
        String street,
        Integer houseNumber
) {
}
