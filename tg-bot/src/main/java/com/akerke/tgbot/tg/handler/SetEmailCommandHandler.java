package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.dao.UserDAO;
import com.akerke.tgbot.exception.UserNotFoundException;
import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.constants.CommonLocale;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
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
        var email = update.getMessage().getText();

        try {
            var user = userDAO.findByEmail(email);
            if(user==null){
                var message = new SendMessage();
                message.setText(localeHelper.get("message.email-not-registered"));
            }
        } catch (UserNotFoundException e) {
            log.error("{}", e.getMessage());
        }


    }


    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.SET_EMAIL;
    }
}
