package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.utils.CommonLocale;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public abstract class TelegramCommandHandler {

    protected final ResponseSender responseSender;
    protected final LocaleHelper localeHelper;

    public abstract void handle(Update update, CommonLocale locale);

    public abstract TelegramCommand commandType();

}
