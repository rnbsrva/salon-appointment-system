package com.akerke.authservice.security.validate;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;
import com.akerke.authservice.repository.UserRepository;
import com.akerke.authservice.security.JwtUtil;
import com.akerke.authservice.utils.Pair;
import com.akerke.authservice.utils.TokenDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.akerke.authservice.reflection.MapUtils.toMap;

@Component
public class RefreshTokenValidator extends TokenValidator {


    public RefreshTokenValidator(JwtUtil jwt, UserRepository userRepository) {
        super(jwt, userRepository);
    }

    public StatusResponse validate(String token) {

    }

    @Override
    public TokenType getType() {
        return TokenType.REFRESH_TOKEN;
    }

}
