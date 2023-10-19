package com.akerke.chatservice.payload.request;

public record ChatCreateRequest(
        Long salonId,
        UserDetailsRequest user
) {
}
