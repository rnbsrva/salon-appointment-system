package com.akerke.chatservice.controller;

import com.akerke.chatservice.payload.request.ChatCreateRequest;
import com.akerke.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    ResponseEntity<?> create(
            @RequestBody ChatCreateRequest createRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chatService.createChat(createRequest));
    }
}
