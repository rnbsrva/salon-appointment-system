package com.akerke.tgbot.tg.handler;

import com.akerke.tgbot.dao.UserDAO;
import com.akerke.tgbot.exception.UserNotFoundException;
import com.akerke.tgbot.feign.NotificationServiceFeignClient;
import com.akerke.tgbot.tg.bot.ResponseSender;
import com.akerke.tgbot.tg.bot.TelegramCommand;
import com.akerke.tgbot.tg.constants.CommonLocale;
import com.akerke.tgbot.tg.entity.Notification;
import com.akerke.tgbot.tg.entity.User;
import com.akerke.tgbot.tg.helper.KeyboardHelper;
import com.akerke.tgbot.tg.helper.LocaleHelper;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ViewAppointmentsCommandHandler extends TelegramCommandHandler {

    private final User user;
    private final NotificationServiceFeignClient feignClient;
    private final UserDAO userDAO;



    public ViewAppointmentsCommandHandler(
            ResponseSender responseSender,
            LocaleHelper localeHelper,
            KeyboardHelper keyboardHelper,
            User user, NotificationServiceFeignClient feignClient, UserDAO userDAO) {
        super(responseSender, localeHelper, keyboardHelper);
        this.user=user;
        this.feignClient = feignClient;
        this.userDAO = userDAO;
    }

    @Override
    public void handle(Update update, CommonLocale locale) {
        try {
            var user = userDAO.findByEmail(this.user.getEmail());
        } catch (UserNotFoundException e) {
            log.error("{}", e.getMessage());
        }

        List<Notification> notifications = this.feignClient.getNotificationsByUser(user.getId());
        var message = new SendMessage();
        if(notifications.isEmpty()){
            message.setText("No appointments found!");
        } else {
            message.setText("Your appointments: " + notifications);
        }
        responseSender.send(message);

    }

    @Override
    public TelegramCommand commandType() {
        return TelegramCommand.VIEW_APPOINTMENTS;
    }


}
