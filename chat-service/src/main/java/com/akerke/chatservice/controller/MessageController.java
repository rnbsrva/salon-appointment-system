package com.akerke.chatservice.controller;

import com.akerke.chatservice.common.handler.WsMessageHandler;
import com.akerke.chatservice.domain.service.MessageService;
import com.akerke.loggerlib.common.annotation.EnableLoggerLib;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("message")
@EnableLoggerLib
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    ResponseEntity<?> get(
            @RequestParam Long salonId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(
                messageService.get(WsMessageHandler.chatMessagesKey(salonId, userId))
        );
    }
}
