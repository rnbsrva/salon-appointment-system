package com.akerke.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig extends CorsConfiguration {

    @Value("${spring.cors.allowed-methods}")
    private String[] allowedMethods;
    @Value("${spring.cors.allowed-headers}")
    private String[] allowedHeaders;

    @Value("${spring.cors.allowed-origin}")
    private String allowedOrigin;
    @Value("${spring.cors.allowed-credentials}")
    private Boolean allowedCredentials;

    @Bean
    public CorsWebFilter corsWebFilter() {
        var cors = new CorsConfiguration();

        cors.setAllowCredentials(allowedCredentials);
        cors.addAllowedOrigin(allowedOrigin);

        cors.setAllowedMethods(List.of(allowedMethods));

        Arrays.stream(allowedHeaders)
                .forEach(cors::addAllowedHeader);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return new CorsWebFilter(source);
    }


}
