package com.akerke.authservice.security.validate;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.payload.response.StatusResponse;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenValidator implements TokenValidator {
    @Override
    public StatusResponse validate(String token) {
        return null;
    }

    @Override
    public TokenType getType() {
        return TokenType.ACCESS_TOKEN;
    }
}
