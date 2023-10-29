package com.akerke.salonservice.common.payload;

public record AddressSearchRequest(
        String state,
        String city,
        String street,
        Integer houseNumber
) {
}
