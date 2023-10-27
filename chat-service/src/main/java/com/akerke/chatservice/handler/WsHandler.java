package com.akerke.chatservice.handler;

import com.akerke.chatservice.payload.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WsHandler {

    @MessageMapping("on_message")
    void onMessage(
            MessageRequest request
    ) {

    }


}
