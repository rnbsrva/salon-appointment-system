package com.akerke.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${spring.auth-service.url}")
    private String authServiceUrl;

    @Bean
    public WebClient authServiceWebClient(){
        return WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }

}
