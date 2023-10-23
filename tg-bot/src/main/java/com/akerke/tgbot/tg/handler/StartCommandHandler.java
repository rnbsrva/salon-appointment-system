package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.utils.CommonLocale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class StartCommandHandler extends TelegramCommandHandler {

    public StartCommandHandler(ResponseSender responseSender, LocaleHelper localeHelper) {
        super(responseSender, localeHelper);
    }

    @Override
    public void handle(
            Update update, CommonLocale locale
    ) {

        var m = new SendMessage();
        m.setChatId(String.valueOf(update.getMessage().getChatId()));
        m.setReplyMarkup(KeyboardHelper.languageModeKeyboard());
        m.setText(localeHelper.get(commandType().getResourceBundleTag(), locale.getLocale()));

        responseSender.send(m);
    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.START;
    }

}
