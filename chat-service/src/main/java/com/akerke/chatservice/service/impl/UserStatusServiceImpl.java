package com.akerke.chatservice.service.impl;

import com.akerke.chatservice.service.UserStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.akerke.chatservice.constants.AppConstants.ONLINE_STAFF_KET_PREFIX;
import static com.akerke.chatservice.constants.AppConstants.ONLINE_USER_KEY;

@Service
@RequiredArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    private final ReactiveStringRedisTemplate redis;


    @Override
    public Mono<Void> setOnline(Long applicationId) {
        return Mono.fromRunnable(() -> {
            redis.opsForSet().add(ONLINE_USER_KEY, String.valueOf(applicationId)).flatMap(l -> {
                return Mono.just(l);
            }); // FIXME: 10/19/2023
        }).then();
    }

    @Override
    public Mono<Void> setOffline(Long applicationId) {
        return Mono.fromRunnable(() -> {
            redis.opsForSet().remove(ONLINE_USER_KEY, String.valueOf(applicationId))
                    .flatMap(l -> {
                        return Mono.just(l);
                    });// FIXME: 10/19/2023
        });
    }

    @Override
    public Mono<Boolean> isOnline(Long applicationId) {
        return redis.opsForSet()
                .isMember(ONLINE_USER_KEY, applicationId);
    }

    private final Function<Long, String> onlineStaffKey = salonId ->
            ONLINE_STAFF_KET_PREFIX.concat(String.valueOf(salonId));

}
