package com.akerke.salonservice.dto;

public record AddressDTO(
         Long houseNumber,
         String street,
         String city,
         String state
) {
}
