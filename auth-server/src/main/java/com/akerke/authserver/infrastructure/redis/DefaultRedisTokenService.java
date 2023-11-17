package com.akerke.authserver.infrastructure.redis;

import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.config.ObjectMapperConfig;
import com.akerke.authserver.domain.model.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DefaultRedisTokenService implements RedisTokenService {

    private final RedisTemplate<String, Token> redisTemplate;
    public final static String ACCESS_TOKEN_HASH_KEY = "ACCESS_TOKENS:";
    public final static String REFRESH_TOKEN_HASH_KEY = "REFRESH_TOKENS:";

    @Override
    public void save(
            Token token
    ) {
        var key = redisKey.apply(token);
        redisTemplate.opsForSet().add(key, token);
        redisTemplate.expire(key, token.getLifeTime(), TimeUnit.SECONDS);
    }

    @Override
    public Set<Token> get(String subject, TokenType type) {
        return redisTemplate.opsForSet().members((type == TokenType.ACCESS ? ACCESS_TOKEN_HASH_KEY : REFRESH_TOKEN_HASH_KEY).concat(subject));
    }

    @Override
    public void clear(String email) {
        redisTemplate.delete(ACCESS_TOKEN_HASH_KEY.concat(email));
        redisTemplate.delete(REFRESH_TOKEN_HASH_KEY.concat(email));
    }

    private final Function<Token, String> redisKey = token ->
            (token.getType() == TokenType.ACCESS ? ACCESS_TOKEN_HASH_KEY : REFRESH_TOKEN_HASH_KEY).concat(token.getSubject());
}
