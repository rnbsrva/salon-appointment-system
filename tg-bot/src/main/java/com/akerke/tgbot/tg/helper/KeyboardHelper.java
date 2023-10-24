package com.akerke.tgbot.tg.helper;

import com.akerke.tgbot.tg.utils.InlineButton;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

@Component
@RequiredArgsConstructor
public class KeyboardHelper {

    private final LocaleHelper localeHelper;

    public final static InlineButton[] LANGUAGE_BUTTONS = {
            new InlineButton("Русский", "change_language_ru"),
            new InlineButton("English", "change_language_en")
    };

    public InlineKeyboardMarkup greetingInlineKeyboardMarkup() {

        var myAppointmentsButton = new InlineKeyboardButton();
        myAppointmentsButton.setText(localeHelper.get("message.my-appointments"));
        myAppointmentsButton.setCallbackData("my-appointments");

        var changeLanguageButton = new InlineKeyboardButton();
        changeLanguageButton.setText(localeHelper.get("message.change-language"));
        changeLanguageButton.setCallbackData("change-language");

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList(myAppointmentsButton));
        keyboard.add(Collections.singletonList(changeLanguageButton));

        return new InlineKeyboardMarkup(keyboard);
    }


    public InlineKeyboardMarkup languageModeKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        Arrays.stream(LANGUAGE_BUTTONS)
                .map(inlineButton -> new ArrayList<InlineKeyboardButton>() {{
                    var btn = new InlineKeyboardButton();
                    btn.setText(inlineButton.command());
                    btn.setCallbackData(inlineButton.callback());
                    add(btn);
                }})
                .forEach(keyboard::add);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
