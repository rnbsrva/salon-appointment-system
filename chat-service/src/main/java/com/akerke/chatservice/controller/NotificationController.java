package com.akerke.chatservice.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    @PostMapping("notify")
    @ApiOperation("Notify the user about a new message")
    void notifyUser() {
    }// TODO: 10/27/2023  receive notification dto and send to user with websocket

}
