package com.akerke.chatservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import static com.akerke.chatservice.constants.AppConstants.ACTIVE_USER_KEY;


@Slf4j
@Configuration(proxyBeanMethods=false)
public class RedisConfig {

    @Bean
    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisProperties redisProperties) {
        var redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisProperties.getHost(),
                redisProperties.getPort()
        );
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return new ReactiveStringRedisTemplate(reactiveRedisConnectionFactory);
    }

    // Redis Atomic Counter to store no. of Active Users.
    @Bean
    RedisAtomicLong activeUserCounter(RedisConnectionFactory redisConnectionFactory) {
        return new RedisAtomicLong(ACTIVE_USER_KEY, redisConnectionFactory);
    }

//    @Bean
//    ApplicationRunner applicationRunner(
//            RedisChatMessageListener redisChatMessageListener
//    ) {
//        return args -> {
//            redisChatMessageListener.subscribeMessageChannelAndPublishOnWebSocket()
//                    .doOnSubscribe(subscription -> log.info("Redis Listener Started"))
//                    .doOnError(throwable -> log.error("Error listening to Redis topic.", throwable))
//                    .doFinally(signalType -> log.info("Stopped Listener. Signal Type: {}", signalType))
//                    .subscribe();
//        };
//    }
}
