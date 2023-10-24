package com.akerke.tgbot.tg.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum CommonLocale {
    EN("en", Locale.ENGLISH),
    RU("ru", Locale.forLanguageTag("ru"));
    private final String tag;
    private final Locale locale;

    public static CommonLocale findByLanguageTag(String languageTag) {
        return languageTag.equals("ru") ? CommonLocale.RU : CommonLocale.EN;
    }

}
