package com.akerke.authserver.infrastructure.redis;

import com.akerke.authserver.domain.model.Token;

import java.util.Optional;
import java.util.Set;

public interface RedisTokenService {

    void save(Token token);

    Set<Token> get(String subject);

    void clear(String email);

}
