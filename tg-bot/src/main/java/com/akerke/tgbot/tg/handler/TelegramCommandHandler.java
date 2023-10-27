package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.constants.CommonLocale;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public abstract class TelegramCommandHandler {

    protected final ResponseSender responseSender;
    protected final LocaleHelper localeHelper;
    protected final KeyboardHelper keyboardHelper;

    public abstract void handle(Update update, CommonLocale locale);

    public abstract TelegramCommand commandType();

    protected String chatIdFromMessage(Update update) {
        return String.valueOf(update.getMessage().getChatId());
    }

    protected String chatIdFromCallback(Update update){
        return String.valueOf(update.getCallbackQuery().getMessage().getChatId());
    }

}
