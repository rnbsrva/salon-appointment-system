package com.akerke.chatservice.service;

import org.springframework.web.socket.WebSocketSession;

public interface WebSocketSessionManager {
    void addWebSocketSession(WebSocketSession webSocketSession);

    void removeWebSocketSession(WebSocketSession webSocketSession);

    WebSocketSession getWebSocketSessions(String id);
}
