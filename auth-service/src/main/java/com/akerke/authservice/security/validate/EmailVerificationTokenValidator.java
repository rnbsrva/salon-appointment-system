package com.akerke.authservice.security.validate;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.repository.UserRepository;
import com.akerke.authservice.security.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class EmailVerificationTokenValidator extends TokenValidator {


    public EmailVerificationTokenValidator(JwtUtil jwt, UserRepository userRepository) {
        super(jwt, userRepository);
    }

    public StatusResponse validate(String token) {
        return null;
    }

    @Override
    public TokenType getType() {
        return TokenType.EMAIL_VERIFICATION_TOKEN;
    }

}
