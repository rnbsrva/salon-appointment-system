package com.akerke.tgbot.tg.constants;

import java.util.List;
import java.util.Locale;

public class AppConstants {
    public final static List<Locale> ENABLE_LOCALES =
            List.of(
                    Locale.ENGLISH,
                    Locale.forLanguageTag("ru"),
                    Locale.forLanguageTag("kz")
            );
}
