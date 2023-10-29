package com.akerke.salonservice.common.payload;

public record SalonSearchRequest (
        String name,
        String treatmentToFind,
        AddressSearchRequest addressDetails
){
}