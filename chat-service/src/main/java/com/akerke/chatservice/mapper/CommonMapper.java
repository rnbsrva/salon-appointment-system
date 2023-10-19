package com.akerke.chatservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonMapper {

    private final ObjectMapper objectMapper;

    public <T> Mono<T> stringToObject(String data, Class<T> clazz) {
        return Mono.fromCallable(() -> objectMapper.readValue(data, clazz))
                .doOnError(throwable -> log.error("Error converting [{}] to class '{}'.", data, clazz.getSimpleName()));
    }

    public <T> Mono<String> objectToString(T object) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(object))
                .doOnError(throwable -> log.error("Error converting [{}] to String.", object));
    }
}
