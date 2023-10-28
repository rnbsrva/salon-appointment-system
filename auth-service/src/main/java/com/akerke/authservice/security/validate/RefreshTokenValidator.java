package com.akerke.authservice.security.validate;

import com.akerke.authservice.common.constants.TokenType;
import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.domain.payload.response.StatusResponse;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RefreshTokenValidator extends TokenValidator {

    public RefreshTokenValidator(JwtUtil jwt, UserRepository userRepository) {
        super(jwt, userRepository);
    }

    public StatusResponse validate(String token) {
        var abstractResponse = super.validate(token, TokenType.REFRESH_TOKEN);

        if (abstractResponse.getSuccess() && abstractResponse.getData() instanceof User) {
            return StatusResponse.success(jwt.generateTokenResponse((User) abstractResponse.getData()));
        } else {
            log.error("abstractResponse is not an instance of User");
            return StatusResponse.fail("invalid returned data");
        }

    }


    @Override
    public TokenType getType() {
        return TokenType.REFRESH_TOKEN;
    }

}
