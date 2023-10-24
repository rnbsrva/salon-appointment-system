package com.akerke.authservice.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailLinkMode {
    FORGOT_PASSWORD_LINK("forgot_password"),
    EMAIL_CONFIRMATION_LINK("email_confirmation");

    private final String path;
}
