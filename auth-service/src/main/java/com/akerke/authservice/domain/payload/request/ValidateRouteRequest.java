package com.akerke.authservice.domain.payload.request;

import org.springframework.http.HttpMethod;

public record ValidateRouteRequest(
        String route,
        HttpMethod method,
        String token
) {
}
