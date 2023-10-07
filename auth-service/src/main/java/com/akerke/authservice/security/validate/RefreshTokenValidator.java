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
@RequiredArgsConstructor
public class RefreshTokenValidator implements TokenValidator {

    private final JwtUtil jwt;
    private final UserRepository userRepository;

    @Override
    public StatusResponse validate(String token) {
        var claims = jwt.extractAllClaims(token);
        var sameTokenClaim = TokenValidator.sameTokenClaims(claims, getType());

        if (!sameTokenClaim) {
            return StatusResponse.fail("invalid token type");
        }

        var email = claims.getSubject();

        var optionalUser = userRepository.findByEmail(email);

        return optionalUser
                .map(user -> StatusResponse.success(
                        this.generateTokenResponse(user)
                )).
                orElseGet(() -> StatusResponse.fail("token owner not found"));

    }

    @Override
    public TokenType getType() {
        return TokenType.REFRESH_TOKEN;
    }

    private TokenResponse generateTokenResponse(User user) {
        var accessTokenClaims = toMap(user, new Pair(TOKEN_TYPE_CLAIM_KEY, TokenType.ACCESS_TOKEN));
        var refreshTokenClaims = toMap(user, new Pair(TOKEN_TYPE_CLAIM_KEY, TokenType.REFRESH_TOKEN));

        return new TokenResponse(
                new TokenDetails(
                        TokenType.ACCESS_TOKEN,
                        jwt.generateToken(TokenType.ACCESS_TOKEN, accessTokenClaims, user.getEmail())
                ),
                new TokenDetails(
                        TokenType.REFRESH_TOKEN,
                        jwt.generateToken(TokenType.REFRESH_TOKEN, refreshTokenClaims, user.getEmail())
                ),
                user
        );
    }

}
