package com.akerke.tgbot.tg.config;

import com.akerke.tgbot.tg.bot.NotificationBot;
import com.akerke.tgbot.tg.bot.ResponseSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ComponentCreator {

    private final NotificationBot notificationBot;

    @Bean
    public ResponseSender responseSender(){
        return new ResponseSender(notificationBot);
    }


}
