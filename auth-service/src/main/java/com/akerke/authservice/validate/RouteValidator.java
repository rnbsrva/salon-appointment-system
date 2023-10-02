package com.akerke.authservice.validate;

import org.springframework.http.HttpMethod;

public interface RouteValidator {

    Boolean canActivate(String route, String token, HttpMethod method);

}
