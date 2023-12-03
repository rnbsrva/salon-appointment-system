package com.akerke.chatservice.domain.service.impl;

import com.akerke.chatservice.domain.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static com.akerke.chatservice.common.constants.AppConstants.ONLINE_STAFF_KET_PREFIX;
import static com.akerke.chatservice.common.constants.AppConstants.ONLINE_USER_KEY;

@Service
@RequiredArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    private final RedisTemplate<String, Object> redis;


    @Override
    public void setOnline(Long applicationId) {
        redis.opsForSet()
                .add(ONLINE_USER_KEY, String.valueOf(applicationId));
    }

    @Override
    public void setOffline(Long applicationId) {
        redis.opsForSet()
                .remove(ONLINE_USER_KEY, String.valueOf(applicationId));
    }

    @Override
    public Boolean isOnline(Long applicationId) {
        return redis.opsForSet()
                .isMember(ONLINE_USER_KEY, String.valueOf(applicationId));
    }

    @Override
    public void setOnline(Long salonId, Long applicationId) {
        redis.opsForSet()
                .add(onlineStaffKey.apply(salonId), String.valueOf(applicationId));
    }

    @Override
    public void setOffline(Long salonId, Long applicationId) {
        redis.opsForSet()
                .remove(onlineStaffKey.apply(salonId), String.valueOf(applicationId));
    }

    @Override
    public Boolean supportChatIsOnline(Long salonId) {
        try {
            return redis.opsForSet().size(onlineStaffKey.apply(salonId)) > 0;
        } catch (Exception e) {
            return false;
        }
    }


    private final Function<Long, String> onlineStaffKey = salonId ->
            ONLINE_STAFF_KET_PREFIX.concat(String.valueOf(salonId));

}
