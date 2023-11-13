package com.akerke.authserver.config;

import com.akerke.authserver.domain.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private Integer port;

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        var lettuceConnectionFactory = new LettuceConnectionFactory(host, port);

//        lettuceConnectionFactory.setValidateConnection(true);todo

        return lettuceConnectionFactory;
    }

    @Bean
    public ReactiveRedisTemplate<String, Token> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory
    ) {
        var keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Token> valueSerializer =
                new Jackson2JsonRedisSerializer<>(Token.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Token> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Token> context =
                builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }

}
