package com.akerke.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    @PostMapping("notify")
    void notifyUser() {
    }// TODO: 10/27/2023  receive notification dto and send to user with websocket

}
