package com.akerke.tgbot.tg.helper;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {

    public static ReplyKeyboardMarkup greetingReplyKeyboardMarkup() {

        var row1 = new KeyboardRow();
        row1.add("My account");
        var row2 = new KeyboardRow();
        row2.add("Appointment history");


        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(new ArrayList<>(List.of(row1, row2)))
                .oneTimeKeyboard(false)
                .build();

    }

}
