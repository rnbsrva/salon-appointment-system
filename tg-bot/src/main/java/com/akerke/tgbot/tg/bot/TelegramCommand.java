package com.akerke.tgbot.tg.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramCommand {
    SET_EMAIL("","message.send-email"),
    CHANGE_LANGUAGE("change-language", "message.change-language"),
    START("/start","message.greeting");

    private final String value;
    private final String resourceBundleTag;
}
