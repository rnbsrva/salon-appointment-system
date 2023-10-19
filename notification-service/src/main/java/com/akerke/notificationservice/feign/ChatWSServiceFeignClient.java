package com.akerke.notificationservice.feign;

import com.akerke.notificationservice.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "chat-ws-service")
public interface ChatWSServiceFeignClient {

    @PostMapping(value = "notify")
    void notify(
            @RequestBody NotificationDTO notificationDTO
    );

}
