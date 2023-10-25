package com.akerke.tgbot.tg.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public class ResponseSender {

    private final AbsSender absSender;

    public void send(BotApiMethod<?> botApiMethod) {
        try {
            absSender.execute(botApiMethod);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
