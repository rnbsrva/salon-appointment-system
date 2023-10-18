package com.akerke.tgbot.tg.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramCommand {
    START("/start");

    private final String value;
}
