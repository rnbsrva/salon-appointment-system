package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.utils.CommonLocale;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChangeLanguageCommandHandler extends TelegramCommandHandler {

    public ChangeLanguageCommandHandler(
            ResponseSender responseSender,
            LocaleHelper localeHelper
    ) {
        super(responseSender, localeHelper);
    }

    @Override
    public void handle(
            Update update,
            CommonLocale locale
    ) {

    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.CHANGE_LANGUAGE;
    }

}
