package com.akerke.chatservice.redis;

import com.akerke.chatservice.handler.WsHandler;
import com.akerke.chatservice.mapper.CommonMapper;
import com.akerke.chatservice.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.akerke.chatservice.constants.AppConstants.MESSAGE_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisSubscriber {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final WsHandler ws;
    private final CommonMapper commonMapper;

    public Mono<Void> subscribeMessageChannelAndPublishOnWebSocket() {
        return reactiveStringRedisTemplate.listenTo(new PatternTopic(MESSAGE_TOPIC))
                .map(ReactiveSubscription.Message::getMessage)
                .flatMap(message -> commonMapper.stringToObject(message, Message.class))
                .filter(chatMessage -> !chatMessage.getContent().isEmpty())
                .flatMap(ws::sendMessage)
                .then();
    }

}

