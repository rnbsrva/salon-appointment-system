package com.akerke.chatservice.domain.service.impl;

import com.akerke.chatservice.domain.model.ChatMessage;
import com.akerke.chatservice.domain.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final RedisTemplate<String, Object> redis;
    private final ObjectMapper objectMapper;

    @Override
    public void save(ChatMessage chatMessage, String key) {
        try {
            redis.opsForSet().add(key, objectMapper.writeValueAsString(chatMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ChatMessage> get(String key) {
        var messages = new ArrayList<ChatMessage>();
        var members = redis.opsForSet().members(key);

        if (members != null && !members.isEmpty()) {
            members.stream()
                    .peek(System.out::println)
                    .map(Object::toString)
                    .map(json -> {
                                ChatMessage chatMessage;
                                try {
                                    chatMessage = objectMapper.readValue(json, ChatMessage.class);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                return chatMessage;
                            }
                    )
                    .forEach(System.out::println);
        }

        return messages;
    }

    @Override
    public void clean(String key) {
        redis.delete(key);
    }

    @Override
    public void delete(String key, ChatMessage chatMessage) {
        redis.opsForSet()
                .remove(key, chatMessage);
    }

    @Override
    public void add(String key, ChatMessage chatMessage) {
        redis.opsForSet().add(key, chatMessage);
    }

    @Override
    public Set<String> keys(String pattern) {
        var f = redis.keys(pattern);
        System.out.println(f);
        return redis.keys(pattern);
    }


}

