package com.akerke.chatservice.service.impl;

import com.akerke.chatservice.service.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class WebSocketSessionManagerImpl implements WebSocketSessionManager {
    private final Map<String, WebSocketSession> webSocketSession = new ConcurrentHashMap<>();

    public void addWebSocketSession(WebSocketSession webSocketSession) {
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession) {
    }

    public WebSocketSession getWebSocketSessions(String userId) {
        return webSocketSession.get(userId);
    }
}
