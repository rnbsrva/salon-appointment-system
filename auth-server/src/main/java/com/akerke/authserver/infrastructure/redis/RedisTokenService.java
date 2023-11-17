package com.akerke.authserver.infrastructure.redis;

import com.akerke.authserver.common.constants.TokenType;
import com.akerke.authserver.domain.model.Token;

import java.util.Optional;
import java.util.Set;

public interface RedisTokenService {

    void save(Token token);

    Set<Token> get(String subject, TokenType type);

    void clear(String email);

}
