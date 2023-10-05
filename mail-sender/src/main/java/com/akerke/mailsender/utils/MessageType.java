package com.akerke.mailsender.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {
    FORGOT_PASSWORD("forgot_password.ftl"),
    CONFIRM_EMAIL("confirm_email.ftl");

    private final String template;
}
