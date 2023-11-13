package com.akerke.authserver.infrastructure.redis;

import com.akerke.authserver.domain.model.Token;

import java.util.Optional;

public interface RedisTokenService {

    void save(Token token);

    Optional<Token> get();

}
