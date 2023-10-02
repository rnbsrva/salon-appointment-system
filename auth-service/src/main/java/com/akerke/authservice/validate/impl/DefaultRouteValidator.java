package com.akerke.authservice.validate.impl;

import com.akerke.authservice.validate.RouteValidator;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class DefaultRouteValidator implements RouteValidator {

    @Override
    public Boolean canActivate(String route, String token, HttpMethod method) {
        return null ; // todo
    }

}
