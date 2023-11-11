package com.akerke.exceptionlib.config;

import com.akerke.exceptionlib.handler.GlobalExceptionHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(ExceptionLibProperties.class)
@ConditionalOnClass(ExceptionLibProperties.class)
@ConditionalOnProperty(
        prefix = "app.exception.lib",
        name = "enabled",
        havingValue = "true"
)
public class ExceptionLibAutoConfiguration {

    @PostConstruct
    void init() {
        log.info("ExceptionLibAutoConfiguration initialized ");
    }


    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        log.info("created GlobalExceptionHandler");
        return new GlobalExceptionHandler();
    }
}
