package com.akerke.tgbot.tg.bot;

import com.akerke.tgbot.dao.UserDAO;
import com.akerke.tgbot.tg.handler.ChangeLanguageCommandHandler;
import com.akerke.tgbot.tg.handler.SetEmailCommandHandler;
import com.akerke.tgbot.tg.handler.StartCommandHandler;
import com.akerke.tgbot.tg.handler.TelegramCommandHandler;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.constants.CommonLocale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class NotificationBot extends TelegramLongPollingBot {

    @Value("${spring.telegram.bot.username}")
    private String botUsername;

    @Value("${spring.telegram.bot.token}")
    private String botToken;

    private Map<TelegramCommand, TelegramCommandHandler> commandMap;

    public NotificationBot(
            LocaleHelper localeHelper,
            KeyboardHelper keyboardHelper,
            UserDAO userDAO
    ) {
        this.init(localeHelper, keyboardHelper, userDAO);
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdateReceived(
            Update update
    ) {

        if (update.hasMessage() && "/start".equals(update.getMessage().getText())) {
            commandMap.get(TelegramCommand.START).handle(update, CommonLocale.EN);
        }

        if (update.hasCallbackQuery()) {
            var callback = update.getCallbackQuery();

            if (callback.getData().startsWith("change_language")) {
                commandMap.get(TelegramCommand.CHANGE_LANGUAGE).handle(update, CommonLocale.EN);
            }
        }
    }

    private void init(LocaleHelper localeHelper,
                      KeyboardHelper keyboardHelper,
                      UserDAO userDAO) {
        final ResponseSender responseSender = new ResponseSender(this);
        this.commandMap = new HashMap<>() {{
            put(TelegramCommand.CHANGE_LANGUAGE, new ChangeLanguageCommandHandler(
                    responseSender,
                    localeHelper,
                    keyboardHelper,
                    userDAO
            ));
            put(TelegramCommand.START, new StartCommandHandler(
                    responseSender,
                    localeHelper,
                    keyboardHelper,
                    userDAO
            ));
            put(TelegramCommand.SET_EMAIL, new SetEmailCommandHandler(
                    responseSender,
                    localeHelper,
                    keyboardHelper,
                    userDAO
            ));
        }};
    }
}
