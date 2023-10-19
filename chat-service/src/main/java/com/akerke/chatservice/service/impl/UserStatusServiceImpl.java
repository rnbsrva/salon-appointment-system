package com.akerke.chatservice.service.impl;

import com.akerke.chatservice.service.UserStatusService;
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
            return redis.opsForSet()
                    .add(ONLINE_USER_KEY, String.valueOf(applicationId))
                    .then();
    }

    @Override
    public Mono<Void> setOffline(Long applicationId) {
        return redis.opsForSet()
                .remove(ONLINE_USER_KEY, String.valueOf(applicationId))
                .then();
    }

    @Override
    public Mono<Boolean> isOnline(Long applicationId) {
        return redis.opsForSet()
                .isMember(ONLINE_USER_KEY, applicationId);
    }

    @Override
    public Mono<Void> setOnline(Long salonId, Long applicationId) {
        return redis.opsForSet()
                .add(onlineStaffKey.apply(salonId),String.valueOf(applicationId))
                .then();
    }

    @Override
    public Mono<Void> setOffline(Long salonId, Long applicationId) {
        return redis.opsForSet()
                .remove(onlineStaffKey.apply(salonId),String.valueOf(applicationId))
                .then();
    }

    @Override
    public Mono<Boolean> supportChatIsOnline(Long salonId) {
        return redis.opsForSet()
                .size(onlineStaffKey.apply(salonId))
                .flatMap(size -> Mono.just(size > 0));
    }


    private final Function<Long, String> onlineStaffKey = salonId ->
            ONLINE_STAFF_KET_PREFIX.concat(String.valueOf(salonId));

}
