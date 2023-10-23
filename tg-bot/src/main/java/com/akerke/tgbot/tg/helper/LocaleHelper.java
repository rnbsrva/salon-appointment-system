package com.akerke.tgbot.tg.helper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LocaleHelper {

    private final ResourceBundleMessageSource resourceBundle;
    private Locale defaultLocale;

    @PostConstruct
    private void postConstruct() {
        this.defaultLocale = Locale.ENGLISH;
        this.setLocale(this.defaultLocale);
    }

    public void setLocale(Locale locale) {
        resourceBundle.setDefaultLocale(locale);
    }

    public String get(String code, Locale locale, Object... vararg) {
        return resourceBundle.getMessage(code, vararg, locale);
    }

    public String get(String code) {
        return resourceBundle.getMessage(code, null, this.defaultLocale);
    }


}
