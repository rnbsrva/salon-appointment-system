package com.akerke.authserver.domain.model;

import com.akerke.authserver.common.constants.TokenType;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash
public class Token implements Serializable {

    private String userId;
    private TokenType type;
    private LocalDateTime created;
    private Long lifeTime;
    private String value;

}
