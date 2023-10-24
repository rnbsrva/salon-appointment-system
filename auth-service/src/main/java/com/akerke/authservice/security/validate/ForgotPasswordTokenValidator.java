package com.akerke.authservice.security.validate;

import com.akerke.authservice.common.constants.TokenType;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.security.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class ForgotPasswordTokenValidator extends TokenValidator {

    public ForgotPasswordTokenValidator(JwtUtil jwt, UserRepository userRepository) {
        super(jwt, userRepository);
    }

    public StatusResponse validate(String token) {
        return super.validate(token,getType());
    }

    @Override
    public TokenType getType() {
        return TokenType.FORGOT_PASSWORD_TOKEN;
    }

}
