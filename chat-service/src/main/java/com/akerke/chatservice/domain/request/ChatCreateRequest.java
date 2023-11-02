package com.akerke.chatservice.domain.request;

public record ChatCreateRequest(
        Long salonId,
        UserDetailsRequest user
) {
}
