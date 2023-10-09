package com.akerke.chatservice.redis;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisPublisher {

    private final RedisCommands<String,String> redisCommands;

    public void publish(String channel, String message) {
        log.info("going to publish the message to channel {} and message = {}", channel, message);
        redisCommands.publish(channel, message);
    }

}

