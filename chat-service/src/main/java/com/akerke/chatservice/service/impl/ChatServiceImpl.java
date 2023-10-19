package com.akerke.chatservice.service.impl;

import com.akerke.chatservice.mapper.ChatMapper;
import com.akerke.chatservice.payload.request.ChatCreateRequest;
import com.akerke.chatservice.repository.ChatReactiveRepository;
import com.akerke.chatservice.repository.UserReactiveRepository;
import com.akerke.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatReactiveRepository chatReactiveRepository;
    private final ChatMapper chatMapper;
    private final UserReactiveRepository userReactiveRepository;

    @Override
    public Mono<ServerResponse> createChat(ServerRequest req) {

        return req.bodyToMono(ChatCreateRequest.class)
                .flatMap(createRequest -> {
                    var chat = chatMapper.toModel(createRequest);
                    return userReactiveRepository.save(chat.getUser())
                            .flatMap(savedUser -> {
                                chat.setUser(savedUser);
                                return chatReactiveRepository.save(chat)
                                        .flatMap(savedChat -> ServerResponse
                                                .status(HttpStatus.CREATED).
                                                bodyValue(savedChat));
                            });
                })
                .onErrorResume(e -> {
                    System.out.println(e.getMessage());
                    return null;
                });
    }
}
