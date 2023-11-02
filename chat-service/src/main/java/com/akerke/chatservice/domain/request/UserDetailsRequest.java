package com.akerke.chatservice.domain.request;

public record UserDetailsRequest(
        String name,
        String surname,
        Long applicationId
) {
}
