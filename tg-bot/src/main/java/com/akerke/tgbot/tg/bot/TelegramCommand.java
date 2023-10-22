package com.akerke.tgbot.tg.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramCommand {
    CHANGE_LANGUAGE("change_language"),
    START("/start");

    private final String value;
}
