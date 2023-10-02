package com.akerke.authservice.payload.request;

import org.springframework.http.HttpMethod;

public record ValidateRouteRequest(
        String route,
        HttpMethod method,
        String token
) {
}
