package com.akerke.chatservice.config;

import com.akerke.chatservice.redis.RedisSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@Configuration(proxyBeanMethods = false)
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("spring.data.redis.port")
    private Integer redisPort;

    @Bean
    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        var redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisHost,
                redisPort
        );
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    ReactiveStringRedisTemplate reactiveStringRedisTemplate(
            @Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory fct
    ) {
        return new ReactiveStringRedisTemplate(fct);
    }

    //     Redis Atomic Counter to store no. of Active Users.
    @Bean
    RedisAtomicLong activeUserCounter(RedisConnectionFactory fct) {
        return new RedisAtomicLong(ACTIVE_USER_KEY, fct);
    }


    @Bean
    ApplicationRunner applicationRunner(
            RedisSubscriber redisSubscriber
    ) {
        return args -> {
            redisSubscriber.subscribeMessageChannelAndPublishOnWebSocket()
                    .doOnSubscribe(subscription -> log.info("Redis Listener Started"))
                    .doOnError(throwable -> log.error("Error listening to Redis topic.", throwable))
                    .doFinally(signalType -> log.info("Stopped Listener. Signal Type: {}", signalType))
                    .subscribe();
        };
    }
}
