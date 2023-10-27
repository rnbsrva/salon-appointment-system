package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.dao.UserDAO;
import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import com.akerke.tgbot.tg.constants.CommonLocale;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChangeLanguageCommandHandler extends TelegramCommandHandler {

    private final UserDAO userDao;

    public ChangeLanguageCommandHandler(ResponseSender responseSender,
                                        LocaleHelper localeHelper,
                                        KeyboardHelper keyboardHelper,
                                        UserDAO userDAO) {
        super(responseSender, localeHelper, keyboardHelper);
        this.userDao = userDAO;
    }

    @Override
    public void handle(
            Update update,
            CommonLocale locale
    ) {

        var callback = update.getCallbackQuery();
        var message = new SendMessage();
        message.setChatId(chatIdFromCallback(update));

        if (callback.getData().equals("change-language")) {
            message.setText(localeHelper.get(commandType().getResourceBundleTag()));
            message.setReplyMarkup(keyboardHelper.languageModeKeyboard());

            responseSender.send(message);
        } else {
            var languageTag = languageTag(callback.getData());
            var commonLocale = CommonLocale.findByLanguageTag(languageTag);

            var user = userDao.findByTelegramId(callback.getFrom().getId());
            user.setLocale(commonLocale);
            userDao.update(user);

            message.setText(localeHelper.get("message.lang-updated", commonLocale.getLocale()));
        }
        responseSender.send(message);

    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.CHANGE_LANGUAGE;
    }

    private String languageTag(String data) {
        return data.substring(data.lastIndexOf('_'));
    }

}
