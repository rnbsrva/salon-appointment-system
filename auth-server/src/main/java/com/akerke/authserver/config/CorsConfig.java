package com.akerke.authserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${spring.cors.mapping}")
    private String mapping;
    @Value("${spring.cors.allowed-origins}")
    private String origins;
    @Value("${spring.cors.allowed-methods}")
    private String methods;
    @Value("${spring.cors.allowed-headers}")
    private String headers;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }
}
