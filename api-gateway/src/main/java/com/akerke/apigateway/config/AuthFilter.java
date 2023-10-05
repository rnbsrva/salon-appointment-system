package com.akerke.apigateway.config;

import com.akerke.apigateway.utils.StatusResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class AuthFilter implements GatewayFilter {

    private final WebClient webClient;

    public AuthFilter(@Qualifier("authServiceWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        var request = exchange.getRequest();

        if (this.authMissing.test(request)) {
            return onError(exchange);
        }

        final var token = request.getHeaders().getOrEmpty("Authorization").get(0);

        var res = webClient.get()
                .uri(validateTokenUrl(token)
                ).retrieve()
                .bodyToMono(StatusResponse.class);

        return res.flatMap(statusResponse -> {
            if (statusResponse.success()) {
                return chain.filter(exchange);
            } else {
                return onError(exchange);
            }
        });

    }

    private static Function<UriBuilder, URI> validateTokenUrl(String token) {
        return uriBuilder ->
                uriBuilder.path("validate-token")
                        .queryParam("token_type", "ACCESS_TOKEN")
                        .queryParam("token", token)
                        .build();
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private final Predicate<ServerHttpRequest> authMissing = r -> !r.getHeaders().containsKey("Authorization");


}