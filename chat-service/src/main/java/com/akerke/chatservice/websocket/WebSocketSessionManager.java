package com.akerke.chatservice.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class WebSocketSessionManager {
    private final Map<String, WebSocketSession> webSocketSession = new ConcurrentHashMap<>();

    public void addWebSocketSession(
            String sessionKey,
            WebSocketSession webSocketSession
    ) {
        this.webSocketSession.put(sessionKey, webSocketSession);
        log.info("added session id {} for session id {}", webSocketSession.getId(), sessionKey);
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession) {
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);
        log.info("got request to remove session id {} for user id {}", webSocketSession.getId(), userId);
        this.webSocketSession.remove(userId);
        log.info("removed session id {} for user id {}", webSocketSession.getId(), userId);
    }

    public WebSocketSession getWebSocketSessions(String userId) {
        return this.webSocketSession.get(userId);
    }
}
