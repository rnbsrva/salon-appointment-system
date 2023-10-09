package com.akerke.chatservice.redis;

import com.akerke.chatservice.service.WebSocketSessionManager;
import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Slf4j
public class RedisSubscriberHelper implements RedisPubSubListener<String, String> {

    private WebSocketSessionManager webSocketSessionManager;

    public RedisSubscriberHelper(WebSocketSessionManager webSocketSessionManager){
        this.webSocketSessionManager = webSocketSessionManager;
    }
    @Override
    public void message(String channel, String message) {
        log.info("got the message on redis "+ channel+ " and "+ message);
        var ws = this.webSocketSessionManager.getWebSocketSessions(channel);
        try {
            ws.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void message(String s, String k1, String s2) {

    }

    @Override
    public void subscribed(String s, long l) {

    }

    @Override
    public void psubscribed(String s, long l) {

    }

    @Override
    public void unsubscribed(String s, long l) {

    }

    @Override
    public void punsubscribed(String s, long l) {

    }
}
