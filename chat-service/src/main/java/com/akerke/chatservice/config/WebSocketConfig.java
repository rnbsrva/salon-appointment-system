package com.akerke.chatservice.config;

import com.akerke.chatservice.websocket.WebSocketSessionHandshakeInterceptor;
import com.akerke.chatservice.websocket.WebSocketTextHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketTextHandler webSocketTextHandler;
    private final WebSocketSessionHandshakeInterceptor webSocketSessionHandshakeInterceptor;

    @Value("${spring.ws.allowed-origins}")
    private String wsAllowedOrigins;

    @Override
    public void registerWebSocketHandlers(
            @NonNull WebSocketHandlerRegistry registry
    ) {
        registry.addHandler(webSocketTextHandler)
                .addInterceptors(webSocketSessionHandshakeInterceptor)
                .setAllowedOrigins(wsAllowedOrigins);
    }

}
