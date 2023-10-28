package com.akerke.apigateway.config;

import com.akerke.apigateway.utils.StatusResponse;
import jakarta.annotation.PostConstruct;
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
    void test(){
        System.out.println("hi");
    }
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        var request = exchange.getRequest();

       request.getHeaders().entrySet()
                       .forEach(System.out::println);
        System.out.println("new request " + request.getURI());
        if (this.authHeaderMissing.test(request)) {
            logger.info("auth missing " + request.getURI());
            return onAuthError(exchange);
        }

        final var token = request.getHeaders().getOrEmpty("Authorization").get(0);

        var res = webClient.get()
                .uri(validateTokenUrl(token))
                .retrieve()
                .bodyToMono(StatusResponse.class);

        return res.flatMap(statusResponse -> statusResponse.success() ? chain.filter(exchange) : onAuthError(exchange));

    }

    private static Function<UriBuilder, URI> validateTokenUrl(String token) {
        return uriBuilder ->
                uriBuilder.path("validate-token")
                        .queryParam("access_token", token.substring("Bearer ".length()))
                        .build();
    }

    private Mono<Void> onAuthError(ServerWebExchange exchange) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}
