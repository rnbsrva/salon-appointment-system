package com.akerke.authservice.payload.response;

public record StatusResponse(
        Boolean success,
        Object data
) {
    public StatusResponse(Boolean success) {
        this(
                success,
                null
        );
    }


}
