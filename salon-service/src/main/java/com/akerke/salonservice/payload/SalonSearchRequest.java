package com.akerke.salonservice.payload;

public record SalonSearchRequest (
        String name,
        String treatmentToFind,
        AddressSearchRequest addressDetails
){
}