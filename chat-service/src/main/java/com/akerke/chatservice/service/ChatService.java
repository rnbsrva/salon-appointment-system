package com.akerke.chatservice.service;

import com.akerke.chatservice.model.Chat;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ChatService {

    Mono<ServerResponse> createChat(ServerRequest req);

    Mono<Void> deleteChat(Chat chat);

    Flux<Chat> findChatsByCreatedAtBefore(Mono<LocalDateTime> time);
}
