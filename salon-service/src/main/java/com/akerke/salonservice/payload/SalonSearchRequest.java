package com.akerke.salonservice.payload;

public record SalonSearchRequest (
        String name,
        AddressSearchRequest addressDetails
){
}
