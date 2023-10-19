package com.akerke.chatservice.router;

import com.akerke.chatservice.service.ChatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ChatRouter {

    @Bean
    RouterFunction<ServerResponse> initializeChatRoutes(
            ChatService chatService
    ) {
        return route(POST("/chat"), chatService::createChat);
    }

}
