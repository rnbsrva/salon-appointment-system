package com.akerke.tgbot.tg.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
public enum TelegramCommand {
    CHANGE_LANGUAGE("change_language", ""),
    START("/start","message.greeting");

    private final String value;
    private final String resourceBundleTag;
}
