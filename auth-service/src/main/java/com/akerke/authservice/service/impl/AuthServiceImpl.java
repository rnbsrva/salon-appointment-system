package com.akerke.authservice.service.impl;

import com.akerke.authservice.constants.EmailLinkMode;
import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.kafka.KafkaEmailMessageDetails;
import com.akerke.authservice.kafka.KafkaProducer;
import com.akerke.authservice.payload.request.ResetPasswordRequest;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;
import com.akerke.authservice.security.EmailLinkHelper;
import com.akerke.authservice.security.JwtUtil;
import com.akerke.authservice.service.AuthService;
import com.akerke.authservice.service.UserService;
import com.akerke.authservice.utils.Pair;
import com.akerke.authservice.utils.TokenDetails;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.akerke.authservice.reflection.MapUtils.toMap;

import static com.akerke.authservice.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwt;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafka;
    private final EmailLinkHelper linkHelper;

    public static final String TOKEN_TYPE_CLAIM_KEY = "token_type";

    @Override
    public StatusResponse validateToken(String token, TokenType type) {
        try {
            var claims = jwt.extractAllClaims(token);
            var sameTokenClaim = sameTokenClaims(claims, type);

            if (!sameTokenClaim) {
                return new StatusResponse(false, "invalid claims");
            }

            var email = claims.getSubject();
            var user = userService.findByEmail(email);

            switch (type) {
                case ACCESS_TOKEN -> {
                    return StatusResponse.success(user);
                }
                case REFRESH_TOKEN -> {
                    return StatusResponse.success(token(user));
                }
                case VERIFICATION_TOKEN -> {
                    userService.verifyEmail(user);
                    return StatusResponse.success("email verified");
                }
                default -> {
                    throw new IllegalArgumentException();
                }
            }

        } catch (Exception e) {
            return StatusResponse.fail(e.getMessage());
        }

    }

    @Override
    public TokenResponse token(User user) {
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

    @Override
    public StatusResponse resetPassword(
            ResetPasswordRequest req
    ) {
        var user = userService.findByEmail(req.email());

        var validPassword = passwordEncoder.matches(req.oldPassword(), user.getPassword());

        if (!validPassword) {
            return StatusResponse.fail("invalid password");
        }

        user.setPassword(passwordEncoder.encode(req.newPassword()));
        userService.update(user);
        return StatusResponse.success();
    }

    @Override
    public StatusResponse forgotPassword(String email) {
        var user = userService.findByEmail(email);
        log.info("user {} forgot password", user.getId());

        var msg = KafkaEmailMessageDetails
                .builder()
                .subject("Forgot password action")
                .recipient(email)
                .msgBody(linkHelper.link(EmailLinkMode.FORGOT_PASSWORD_LINK, email))
                .build();

        kafka.produce(KAFKA_PASSWORD_FORGOT_TOPIC, msg);

        return StatusResponse.success();
    }


    private Boolean sameTokenClaims(Claims claims, TokenType type) {
        return claims.containsKey(TOKEN_TYPE_CLAIM_KEY) && type == TokenType.valueOf(claims.get(TOKEN_TYPE_CLAIM_KEY).toString());
    }


}
