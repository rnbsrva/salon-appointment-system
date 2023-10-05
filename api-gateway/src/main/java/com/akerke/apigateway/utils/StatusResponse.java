package com.akerke.apigateway.utils;

public record StatusResponse(
        Boolean success,
        Object data
) {
}
