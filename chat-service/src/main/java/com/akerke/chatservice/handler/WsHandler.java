package com.akerke.chatservice.handler;

import com.akerke.chatservice.mapper.CommonMapper;
import com.akerke.chatservice.model.Message;
import com.akerke.chatservice.redis.RedisPublisher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@Slf4j
public class WsHandler implements WebSocketHandler {

    private final Sinks.Many<Message> chatMessageSink;
    private final Flux<Message> chatMessageFluxSink;
    private final RedisPublisher redisChatMessagePublisher;
    private final CommonMapper commonMapper;

    @Override
    public Mono<Void> handle(
            @NonNull WebSocketSession session
    ) {
        return null;
    }

    public Mono<Sinks.EmitResult> sendMessage(Message chatMessage) {
        return Mono.fromSupplier(() -> chatMessageSink.tryEmitNext(chatMessage))
                .doOnSuccess(emitResult -> {
                            if (emitResult.isFailure()) {
                                log.error("Failed to send message with id: {}", chatMessage.getId());
                            }
                        }
                );
    }

}
