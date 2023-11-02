package com.akerke.chatservice.controller;

import com.akerke.chatservice.domain.request.ChatCreateRequest;
import com.akerke.chatservice.domain.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("Chat API")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @ApiOperation("Create a new chat")
    ResponseEntity<?> create(
            @RequestBody ChatCreateRequest createRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chatService.createChat(createRequest));
    }
}
