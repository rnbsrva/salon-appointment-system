package com.akerke.authserver.domain.dto;

import org.springframework.http.HttpMethod;

public record RouteValidateDTO(
        HttpMethod httpMethod,
        String route,
        String token
) {
}
