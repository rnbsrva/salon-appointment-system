package com.akerke.tgbot.tg.helper;

import com.akerke.tgbot.tg.utils.InlineButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardHelper {

    public final static InlineButton[] LANGUAGE_BUTTONS = {
            new InlineButton("Қазақ", "language_btn_kz"),
            new InlineButton("Русский", "language_btn_ru"),
            new InlineButton("English", "language_btn_en")
    };

    public static ReplyKeyboardMarkup greetingReplyKeyboardMarkup() {


        var row1 = new KeyboardRow();
        row1.add("My account");
        var row2 = new KeyboardRow();
        row2.add("Appointment history");
        var row3 = new KeyboardRow();
        row3.add("Appointment history");

        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(new ArrayList<>(List.of(row1, row2)))
                .oneTimeKeyboard(false)
                .build();

    }

    public static InlineKeyboardMarkup languageModeKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        Arrays.stream(LANGUAGE_BUTTONS)
                .map(inlineButton -> new ArrayList<InlineKeyboardButton>(){{
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
