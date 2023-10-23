package com.akerke.tgbot.tg.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LocaleHelper {

    private final ResourceBundleMessageSource resourceBundle;

    public void setLocale(Locale locale) {
        resourceBundle.setDefaultLocale(locale);
    }

    public String get(String code, Locale locale, Object... vararg) {
        return resourceBundle.getMessage(code, vararg, locale);
    }

}
