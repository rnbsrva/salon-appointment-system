package com.akerke.authservice.common.utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;


public record LogBody(
        HttpStatus httpStatus,
        String url,
        HttpMethod method
) {
    public String toString(){
        return """
                %s, %s, %s
                """.formatted(httpStatus.name(),url,method.name());
    }
}