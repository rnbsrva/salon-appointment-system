package com.akerke.chatservice.controller;

import com.akerke.chatservice.domain.request.ChatCreateRequest;
import com.akerke.chatservice.domain.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    @ApiOperation("Delete chat by id")
    void deleteChat(
            @PathVariable String id
    ) {
        chatService.deleteChat(id);
    }

    @GetMapping("{id}")
    ResponseEntity<?> getChat(
            @PathVariable String id
    ){
        return ResponseEntity
                .ok(chatService.getById(id));
    }



}
