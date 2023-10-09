package com.akerke.chatservice.websocket;

import com.akerke.chatservice.redis.RedisPublisher;
import com.akerke.chatservice.redis.RedisSubscriber;
import com.akerke.chatservice.service.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketTextHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager webSocketSessionManager;
    private final RedisPublisher redisPublisher;
    private final RedisSubscriber redisSubscriber;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.webSocketSessionManager.addWebSocketSession(session);
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(session);
        this.redisSubscriber.subscribe(userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("ws connection closed reason: {}", status.getReason());
        this.webSocketSessionManager.removeWebSocketSession(session);
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(session);
        this.redisSubscriber.unsubscribe(userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }


}
