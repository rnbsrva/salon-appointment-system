package com.akerke.authservice.common.validate;

import org.springframework.http.HttpMethod;

public interface RouteValidator {

    Boolean canActivate(String route, String token, HttpMethod method);

}
