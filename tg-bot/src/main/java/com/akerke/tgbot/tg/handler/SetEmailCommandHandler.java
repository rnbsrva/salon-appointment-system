package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.dao.UserDAO;
import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.utils.CommonLocale;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SetEmailCommandHandler extends TelegramCommandHandler {

    private final UserDAO userDAO;

    public SetEmailCommandHandler(
            ResponseSender responseSender,
            LocaleHelper localeHelper,
            KeyboardHelper keyboardHelper,
            UserDAO userDAO
    ) {
        super(responseSender, localeHelper, keyboardHelper);
        this.userDAO = userDAO;
    }

    @Override
    public void handle(
            Update update,
            CommonLocale locale
    ) {

    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.SET_EMAIL;
    }
}
