package com.akerke.notificationservice.kafka;

import com.akerke.notificationservice.dto.NotificationDTO;
import com.akerke.notificationservice.feign.ChatWSServiceFeignClient;
import com.akerke.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationListener {

    private final ChatWSServiceFeignClient feignClient;
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "notification", groupId = "0"
    )
    void listenNotifications(NotificationDTO notificationDTO){
        log.info("new notification sending {}", notificationDTO.toString());
        notificationService.save(notificationDTO);
        feignClient.notify(notificationDTO);
    }

}
