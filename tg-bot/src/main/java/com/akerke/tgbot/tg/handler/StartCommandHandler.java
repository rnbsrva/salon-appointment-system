package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StartCommandHandler implements TelegramCommandHandler {

    private final ResponseSender responseSender;

    @Override
    public void handle(
            Update update
    ) {

        var m = new SendMessage();
        m.setChatId(String.valueOf(update.getMessage().getChatId()));
        m.setReplyMarkup(KeyboardHelper.languageModeKeyboard());
        m.setText("choose");

        responseSender.send(m);
    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.START;
    }

}
