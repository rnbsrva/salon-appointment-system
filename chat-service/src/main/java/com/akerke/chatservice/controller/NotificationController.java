package com.akerke.chatservice.controller;

import com.akerke.chatservice.domain.dto.NotificationDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api("Notification API")
public class NotificationController {

    private final SimpMessagingTemplate ws;

    @PostMapping("notify")
    @ApiOperation("Notify the user about a new message")
    void notifyUser(NotificationDTO notificationDTO) {
        ws.convertAndSend("/topic/notification/" + notificationDTO.recipientId(), notificationDTO);
    }
}
