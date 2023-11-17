package com.akerke.authserver.domain.model;

import com.akerke.authserver.common.constants.TokenType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash
@Builder
@Data
public class Token implements Serializable {

    private String subject;
    private TokenType type;
    private LocalDateTime created;
    private Long lifeTime;
    private String value;

}
