package com.akerke.tgbot.tg.bot;

import com.akerke.tgbot.tg.handler.ChangeLanguageCommandHandler;
import com.akerke.tgbot.tg.handler.StartCommandHandler;
import com.akerke.tgbot.tg.handler.TelegramCommandHandler;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.utils.CommonLocale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NotificationBot extends TelegramLongPollingBot {

    @Value("${spring.telegram.bot.username}")
    private String botUsername;

    @Value("${spring.telegram.bot.token}")
    private String botToken;

    private Map<TelegramCommand, TelegramCommandHandler> commandMap;

    public NotificationBot(LocaleHelper localeHelper) {
        final ResponseSender responseSender = new ResponseSender(this);
        this.commandMap =new HashMap<>(){{
            put(TelegramCommand.CHANGE_LANGUAGE, new ChangeLanguageCommandHandler(responseSender,localeHelper));
            put(TelegramCommand.START, new StartCommandHandler(responseSender,localeHelper));
        }};
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

        if ("/start".equals(update.getMessage().getText())) {
            commandMap.get(TelegramCommand.START).handle(update, CommonLocale.EN);
        }

    }
}
