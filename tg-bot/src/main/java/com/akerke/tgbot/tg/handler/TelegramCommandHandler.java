package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.TelegramCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramCommandHandler {

    void handle(Update update);

    TelegramCommand commandType();

}
