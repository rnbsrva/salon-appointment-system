package com.akerke.tgbot.feign;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name="feedback-service")
public interface NotificationServiceClient {

//    List<NotificationDTO> findUserNotifications(Long )
}
