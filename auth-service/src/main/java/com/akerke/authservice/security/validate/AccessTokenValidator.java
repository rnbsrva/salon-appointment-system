package com.akerke.authservice.security.validate;

import com.akerke.authservice.common.constants.TokenType;
import com.akerke.authservice.domain.payload.response.StatusResponse;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.security.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenValidator extends TokenValidator {

    public AccessTokenValidator(JwtUtil jwt, UserRepository userRepository) {
        super(jwt, userRepository);
    }

    @Override
    public StatusResponse validate(String token) {
        return super.validate(token, getType());
    }

    @Override
    public TokenType getType() {
        return TokenType.ACCESS_TOKEN;
    }

}
