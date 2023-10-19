package com.akerke.chatservice.service;

import reactor.core.publisher.Mono;

public interface UserStatusService {

    Mono<Void> setOnline(Long applicationId);

    Mono<Void> setOffline(Long applicationId);

    Mono<Boolean> isOnline(Long applicationId);

    Mono<Void> setOnline(Long salonId, Long applicationId);

    Mono<Void> setOffline(Long salonId, Long applicationId);

    Mono<Boolean> supportChatIsOnline(Long salonId);

}
