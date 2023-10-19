package com.akerke.chatservice.service;

import reactor.core.publisher.Mono;

public interface UserStatusService {
    Mono<Void> setOnline(Long applicationId);

    Mono<Void> setOffline(Long applicationId);

    Mono<Boolean> isOnline(Long applicationId);
}
