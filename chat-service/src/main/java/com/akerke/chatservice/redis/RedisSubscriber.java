package com.akerke.chatservice.redis;

import com.akerke.chatservice.service.WebSocketSessionManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber {

    private final RedisPubSubCommands<String, String> sync;
    private final WebSocketSessionManager webSocketSessionManager;

    public RedisSubscriber(RedisClient redisClient, WebSocketSessionManager webSocketSessionManager) {
        this.webSocketSessionManager = webSocketSessionManager;
        var connection = redisClient.connectPubSub();
        var redisListener = new RedisSubscriberHelper(this.webSocketSessionManager);
        connection.addListener(redisListener);
        this.sync = connection.sync();
    }

    public void subscribe(String channel) {
        this.sync.subscribe(channel);
    }

    public void unsubscribe(String channel) {
        this.sync.unsubscribe(channel);
    }
}
