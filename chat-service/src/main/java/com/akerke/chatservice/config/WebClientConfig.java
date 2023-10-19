package com.akerke.chatservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${spring.application.salon-service-url}")
    private String salonServiceUrl;

    @Bean
    public WebClient salonServiceWebClient() {
        return WebClient.builder()
                .baseUrl(salonServiceUrl)
                .defaultHeaders(httpHeaders -> httpHeaders.add("X-m2m-key", "chat")) // FIXME: 10/19/2023
                .build();
    }
}
