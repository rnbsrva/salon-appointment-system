package com.akerke.apigateway.utils;


import java.io.Serializable;
public record RouteValidateDTO(
        String httpMethod,
        String route,
        String token
) implements Serializable {
}
