package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.dao.UserDAO;
import com.akerke.tgbot.exception.UserNotFoundException;
import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.constants.CommonLocale;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Slf4j
public class StartCommandHandler extends TelegramCommandHandler {

    private final UserDAO userDAO;
    private Map<TelegramCommand, TelegramCommandHandler> commandMap;


    public StartCommandHandler(ResponseSender responseSender, LocaleHelper localeHelper, KeyboardHelper keyboardHelper, UserDAO userDAO) {
        super(responseSender, localeHelper, keyboardHelper);
        this.userDAO = userDAO;
        init(localeHelper, keyboardHelper, userDAO);
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
        commandMap.get(TelegramCommand.SET_EMAIL).handle(update, CommonLocale.EN);
    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.START;
    }


    private void init(LocaleHelper localeHelper,
                      KeyboardHelper keyboardHelper,
                      UserDAO userDAO) {
        this.commandMap = new HashMap<>() {{
            put(TelegramCommand.SET_EMAIL, new SetEmailCommandHandler(
                    responseSender,
                    localeHelper,
                    keyboardHelper,
                    userDAO
            ));
        }};
    }

}
