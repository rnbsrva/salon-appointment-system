package com.akerke.chatservice.redis;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.akerke.chatservice.constants.AppConstants.USER_ONLINE_SET;

@Component
@RequiredArgsConstructor
public class RedisUserStatusChecker {

    private final RedisCommands<String, String> redisCommands;

    public Boolean isOnline(List<Long> userIdList) {
        return new ArrayList<>(redisCommands.smembers(USER_ONLINE_SET)
                .stream()
                .map(Long::valueOf)
                .toList())
                .retainAll(userIdList);
    }

    public void online(Long userId) {
        redisCommands.sadd(USER_ONLINE_SET, String.valueOf(userId));
    }

    public void offline(Long userId) {
        redisCommands.srem(USER_ONLINE_SET, String.valueOf(userId));
    }

}