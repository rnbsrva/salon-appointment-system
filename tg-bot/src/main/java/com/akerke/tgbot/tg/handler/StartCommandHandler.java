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
public class StartCommandHandler extends TelegramCommandHandler {

    private final UserDAO userDAO;

    public StartCommandHandler(ResponseSender responseSender, LocaleHelper localeHelper, KeyboardHelper keyboardHelper, UserDAO userDAO) {
        super(responseSender, localeHelper, keyboardHelper);
        this.userDAO = userDAO;
    }

    @Override
    public void handle(
            Update update, CommonLocale locale
    ) {

        var message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));

        var tgUserId = update.getMessage().getFrom().getId();

        try {
            var user = userDAO.findByTelegramId(tgUserId);
            message.setReplyMarkup(keyboardHelper.greetingInlineKeyboardMarkup());
            message.setText(localeHelper.get(commandType().getResourceBundleTag(), user.getLocale().getLocale()));
        } catch (UserNotFoundException e) {
            log.info("user by id {} not found", tgUserId);
            message.setText(localeHelper.get("message.send-email"));
        }

        responseSender.send(message);
    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.START;
    }

}
