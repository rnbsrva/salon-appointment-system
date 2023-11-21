package com.akerke.chatservice.config;

import com.akerke.chatservice.common.interceptor.LoggingInterceptor;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig {

    @Value("${spring.mvc.allowed.headers}")
    private String allowedHeaders;

    @Value("${spring.mvc.allowed.methods}")
    private String allowedMethods;

    @Value("${spring.mvc.allowed.credentials}")
    private Boolean allowedCredentials;

    @Value("${spring.mvc.mapping}")
    private String mapping;

    @Value("${spring.mvc.allowed.origin-patterns}")
    private String originPatterns;

    @Bean
    public WebMvcConfigurer webMvcConfigurer(
            LoggingInterceptor loggingInterceptor
    ) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(
                    @NonNull CorsRegistry registry
            ) {
                registry.addMapping(mapping)
                        .allowedOriginPatterns(originPatterns)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(allowedCredentials);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
                WebMvcConfigurer.super.addInterceptors(registry);
            }
        };
    }


}