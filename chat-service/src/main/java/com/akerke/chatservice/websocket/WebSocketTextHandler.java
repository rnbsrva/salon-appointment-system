package com.akerke.chatservice.websocket;

import com.akerke.chatservice.redis.RedisPublisher;
import com.akerke.chatservice.redis.RedisSubscriber;
import com.akerke.chatservice.redis.RedisUserStatusChecker;
import lombok.NonNull;
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
    private final RedisUserStatusChecker redisUserStatusChecker;

    @Override
    public void afterConnectionEstablished(
            @NonNull WebSocketSession session
    ) throws Exception {
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(session);
        this.webSocketSessionManager.addWebSocketSession(userId, session);
        this.redisSubscriber.subscribe(userId);
        this.redisUserStatusChecker.online(userId);
    }

    @Override
    public void afterConnectionClosed(
            @NonNull WebSocketSession session,
            CloseStatus status
    ) throws Exception {
        log.info("ws connection closed reason: {}", status.getReason());

        this.webSocketSessionManager.removeWebSocketSession(session);
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(session);
        this.redisSubscriber.unsubscribe(userId);
        this.redisUserStatusChecker.offline(userId);
    }


    @Override
    protected void handleTextMessage(
            @NonNull WebSocketSession session,
            @NonNull TextMessage message
    ) throws Exception {
        super.handleTextMessage(session, message);
    }


}
