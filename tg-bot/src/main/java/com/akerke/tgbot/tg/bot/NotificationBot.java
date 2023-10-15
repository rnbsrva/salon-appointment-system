package com.akerke.tgbot.tg.bot;

import com.akerke.tgbot.tg.helper.KeyboardHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationBot extends TelegramLongPollingBot {

    @Value("${spring.telegram.bot.username}")
    private String botUsername;

    @Value("${spring.telegram.bot.token}")
    private String botToken;


    @Override
    public String getBotUsername(){
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
        if ("/start".equals(update.getMessage().getText())){
            try {
                var m = new SendMessage();
                m.setChatId(String.valueOf(update.getMessage().getChatId()));
                m.setReplyMarkup(KeyboardHelper.greetingReplyKeyboardMarkup());
                m.setText("choose");
                execute(m);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
