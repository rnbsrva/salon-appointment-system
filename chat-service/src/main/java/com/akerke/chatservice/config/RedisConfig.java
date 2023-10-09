package com.akerke.chatservice.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.protocol.RedisCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Bean
    public RedisClient redisClient() {
        return RedisClient.create(new RedisURI(redisHost, redisPort, Duration.ofSeconds(3L)));
    }

    @Bean
    public RedisCommands<String, String> redisCommands(
            RedisClient redisClient
    ) {
        return redisClient.connect().sync();
    }


}
