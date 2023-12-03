package com.akerke.apigateway.config;

import com.akerke.apigateway.utils.RouteValidateDTO;
import com.akerke.apigateway.utils.StatusResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
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
import java.util.logging.Logger;

@Component
public class AuthFilter implements GatewayFilter {

    Logger logger = Logger.getLogger("Gateway");

    private final WebClient webClient;

    public AuthFilter(@Qualifier("authServiceWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    private final Predicate<ServerHttpRequest> authHeaderMissing = r -> !r.getHeaders().containsKey("Authorization");

    @PostConstruct
    void test() {
        System.out.println("hi");
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        var request = exchange.getRequest();

        if (this.authHeaderMissing.test(request)) {
            logger.info("auth missing " + request.getURI());
            return onAuthError(exchange, AuthErrorType.MISSING_BEARER_TOKEN);
        }

        final var token = request.getHeaders().getOrEmpty("Authorization").get(0);

        var res = webClient.post()
                .uri("validate-route")
                .bodyValue(new RouteValidateDTO(
                        request.getMethod().toString(),
                        request.getPath().toString(),
                        token
                ))
                .retrieve()
                .bodyToMono(StatusResponse.class);

        return res.flatMap(
                statusResponse -> statusResponse.success() ?
                        chain.filter(exchange) :
                        onAuthError(exchange, AuthErrorType.INVALID_BEARER_TOKEN)
        );

    }


    private Mono<Void> onAuthError(ServerWebExchange exchange, AuthErrorType errorType) {
        var response = exchange.getResponse();
        response.getHeaders().add("X-Auth-Error", errorType.name().toLowerCase());
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    enum AuthErrorType {
        INVALID_BEARER_TOKEN,
        MISSING_BEARER_TOKEN
    }


}
