package com.akerke.authservice.security.impl;

import com.akerke.authservice.constants.EmailLinkMode;
import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.security.EmailLinkHelper;
import com.akerke.authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.akerke.authservice.security.validate.TokenValidator.TOKEN_TYPE_CLAIM_KEY;


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

        var tokenType = mode == EmailLinkMode.FORGOT_PASSWORD_LINK ? TokenType.FORGOT_PASSWORD_TOKEN : TokenType.EMAIL_VERIFICATION_TOKEN;
        var token = jwt.generateToken(
                tokenType,
                Map.of(
                        TOKEN_TYPE_CLAIM_KEY, tokenType.getName()
                ),
                email
        );

        return finalUrl(mode, token);
    }

    private String finalUrl(EmailLinkMode mode, String token) {
        return rootURL.concat("auth/".concat(mode.getPath()).concat("?verification_token=".concat(token)));
    }

}
