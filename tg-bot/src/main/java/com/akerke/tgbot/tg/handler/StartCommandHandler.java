package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class StartCommandHandler implements TelegramCommandHandler {

    private final ResponseSender responseSender;

    @Override
    public void handle(
            Update update
    ) {
        var m = new SendMessage();
        m.setChatId(String.valueOf(update.getMessage().getChatId()));
        m.setReplyMarkup(KeyboardHelper.greetingReplyKeyboardMarkup());
        m.setText("choose");
        responseSender.send(m);
    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.START;
    }

}
