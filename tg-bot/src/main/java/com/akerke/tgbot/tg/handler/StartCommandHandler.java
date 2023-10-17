package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.TelegramCommand;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommandHandler implements TelegramCommandHandler {

    private AbsSender absSender;

    @Override
    public void handle(
            Update update
    ) {

    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.START;
    }

}
