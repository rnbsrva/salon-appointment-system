package com.akerke.notificationservice.kafka;

import com.akerke.notificationservice.tb.bot.NotificationBot;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNotificationListener {

    private final NotificationBot tg;

    @KafkaListener(
            topics = "tg_notification", groupId = "0"
    )
    void listenNotifications(

    ) {
    }
}
