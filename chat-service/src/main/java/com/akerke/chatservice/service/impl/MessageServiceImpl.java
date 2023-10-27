package com.akerke.chatservice.service.impl;

import com.akerke.chatservice.model.Message;
import com.akerke.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static com.akerke.chatservice.constants.AppConstants.MESSAGE_TOPIC;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final RedisTemplate<String, Object> redis;

    @Override
    public void save(Message message, String key) {
        redis.opsForSet().add(key, message);
    }

    @Override
    public List<Message> get(String key) {
        var messages = new ArrayList<Message>();
        var members = redis.opsForSet().members(key);

        if (members != null && !members.isEmpty()) {
            members.stream().filter(o -> o instanceof Message)
                    .forEach(o -> messages.add((Message) o));
        }

        return messages;
    }

    @Override
    public void clean(String key) {
        redis.delete(key);
    }

}

