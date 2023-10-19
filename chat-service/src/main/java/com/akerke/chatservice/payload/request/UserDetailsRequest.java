package com.akerke.chatservice.payload.request;

public record UserDetailsRequest(
        String name,
        String surname,
        Long applicationId
) {
}
