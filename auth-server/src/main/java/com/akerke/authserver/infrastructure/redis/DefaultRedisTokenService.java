package com.akerke.authserver.infrastructure.redis;

import com.akerke.authserver.domain.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultRedisTokenService implements RedisTokenService{

    private final ReactiveRedisTemplate<String,Token> reactiveRedis;

    @Override
    public void save(Token token) {

    }

    @Override
    public Optional<Token> get() {
        return Optional.empty();
    }
}
