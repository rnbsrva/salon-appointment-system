package com.akerke.chatservice.redis;

import com.akerke.chatservice.model.Message;
import com.akerke.chatservice.payload.request.MessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.akerke.chatservice.constants.AppConstants.MESSAGE_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisPublisher {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final ObjectMapper objectMapper;

    public Mono<Long> publishChatMessage(Message message) {
        return Mono.fromCallable(() -> {
            String msgString = null;
            try {
                msgString = objectMapper.writeValueAsString(message);
            } catch (JsonProcessingException e) {
                log.error("Error converting ChatMessage {} into string", message, e);
            }
            return msgString;
        }).flatMap(msg ->
                reactiveStringRedisTemplate.convertAndSend(MESSAGE_TOPIC, msg)
                        .doOnSuccess(res -> log.debug("Total of {} Messages published to Redis Topic.", res))
                        .doOnError(th -> log.error("Error publishing message.", th))
        );
    }
}

