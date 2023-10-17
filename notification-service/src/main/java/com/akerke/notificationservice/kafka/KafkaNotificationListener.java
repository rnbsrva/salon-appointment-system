package com.akerke.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNotificationListener {


    @KafkaListener(
            topics = "tg_notification", groupId = "0"
    )
    void listenNotifications(
    ) {

    }
}
