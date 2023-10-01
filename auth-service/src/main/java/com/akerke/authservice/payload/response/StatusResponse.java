package com.akerke.authservice.payload.response;

public record StatusResponse(
        Boolean status,
        String message
) {
    public StatusResponse(Boolean status) {
        this(
                status,
                null
        );
    }
}
