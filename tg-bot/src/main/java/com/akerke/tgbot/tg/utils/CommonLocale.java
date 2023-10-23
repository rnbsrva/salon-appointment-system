package com.akerke.tgbot.tg.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonLocale {
    EN("en"),
    RU("ru"),
    KZ("kz");
    private final String tag;
}
