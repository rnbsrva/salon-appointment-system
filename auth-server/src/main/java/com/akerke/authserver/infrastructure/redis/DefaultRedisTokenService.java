package com.akerke.authserver.infrastructure.redis;

import com.akerke.authserver.domain.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DefaultRedisTokenService implements RedisTokenService {

    private final RedisTemplate<String, Token> redisTemplate;
    private final static String TOKEN_HASH_KEY = "ACTIVE_TOKENS:";

    @Override
    public void save(
            Token token
    ) {
        redisTemplate.opsForSet().add(TOKEN_HASH_KEY.concat(token.getSubject()), token);
        redisTemplate.expire(TOKEN_HASH_KEY.concat(token.getSubject()), token.getLifeTime(), TimeUnit.SECONDS);
    }

    @Override
    public Set<Token> get(String subject) {
        return redisTemplate.opsForSet().members(TOKEN_HASH_KEY.concat(subject));
    }

}
