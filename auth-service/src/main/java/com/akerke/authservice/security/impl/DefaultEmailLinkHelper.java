package com.akerke.authservice.security.impl;

import com.akerke.authservice.constants.EmailLinkMode;
import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.security.EmailLinkHelper;
import com.akerke.authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.akerke.authservice.service.impl.AuthServiceImpl.TOKEN_TYPE_CLAIM_KEY;


@Component
@RequiredArgsConstructor
public class DefaultEmailLinkHelper implements EmailLinkHelper {

    private final JwtUtil jwt;

    @Value("${spring.application.root-url}")
    private String rootURL;

    @Override
    public String link(
            EmailLinkMode mode,
            String email
    ) {

        var token = jwt.generateToken(
                TokenType.VERIFICATION_TOKEN,
                Map.of(
                        TOKEN_TYPE_CLAIM_KEY, TokenType.VERIFICATION_TOKEN
                ),
                email
        );

        return finalUrl(mode, token);
    }

    private String finalUrl(EmailLinkMode mode, String token) {
        return rootURL.concat("auth/".concat(mode.getPath()).concat("?verification_token=".concat(token)));
    }

}
