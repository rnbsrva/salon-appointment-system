package com.akerke.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Value("${spring.cloud.gateway.routes.auth-server.url}")
    private String authServerUrl;

    @Value("${spring.cloud.gateway.routes.storage-service.url}")
    private String storageServiceUrl;

    @Value("${spring.cloud.gateway.routes.notification-service.url}")
    private String notificationsServiceUrl;

    @Value("${spring.cloud.gateway.routes.qr-service.url}")
    private String qrServiceUrl;

    @Value("${spring.cloud.gateway.routes.salon-service.url}")
    private String salonServiceUrl;


    @Value("${spring.cloud.gateway.routes.chat-service.url}")
    private String chatServiceUrl;

    @Value("${spring.cloud.gateway.routes.discovery.url}")
    private String discoveryUrl;

    @Bean
    RouteLocator routeLocator(
            RouteLocatorBuilder builder,
            AuthFilter authFilter
    ) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri(authServerUrl))
                .route("chat-service", r -> r.path("/api/v1/chat/**")
                        .filters(f -> f.filter(authFilter)).uri(chatServiceUrl))
                .route("discovery", r -> r.path("/eureka/**")
                        .uri(discoveryUrl))
                .route("salon-service", r -> r.path("/api/v1/salon-service/**")
                        .filters(f -> f.filter(authFilter)).uri(salonServiceUrl))
                .route("qr-service", r -> r.path("/api/v1/qr/**")
                        .uri(qrServiceUrl))
                .route("notification-service", r -> r.path("/api/v1/notification/graphiql")
                        .filters(f -> f.filter(authFilter))
                        .uri(notificationsServiceUrl))
                .route("storage-service", r -> r.path("/api/v1/storage/**")
                        .filters(f -> f.filter(authFilter)).uri(storageServiceUrl))
                .build();
    }

}
