package com.akerke.authservice.security.validate;

import com.akerke.authservice.common.constants.TokenType;
import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.security.JwtUtil;
import com.akerke.authservice.domain.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class EmailVerificationTokenValidator extends TokenValidator {

    private final UserService userService;

    public EmailVerificationTokenValidator(JwtUtil jwt, UserRepository userRepository, UserService userService) {
        super(jwt, userRepository);
        this.userService = userService;
    }

    public StatusResponse validate(String token) {
        var statusResponse = super.validate(token, getType());

        if (!statusResponse.getSuccess()){
            return statusResponse;
        }

        var user = (User) statusResponse.getData();

        userService.verifyEmail(user);
        return StatusResponse.success();
    }

    @Override
    public TokenType getType() {
        return TokenType.EMAIL_VERIFICATION_TOKEN;
    }

}
