package com.akerke.chatservice.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ChatService {

    Mono<ServerResponse> createChat(ServerRequest req);

}
