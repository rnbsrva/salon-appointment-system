package com.akerke.authservice.payload.response;

public record StatusResponse(
        Boolean status,
        String message
) {
}
