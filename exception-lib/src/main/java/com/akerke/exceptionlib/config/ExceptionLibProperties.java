package com.akerke.exceptionlib.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@ConfigurationProperties("app.exception.lib")
public class ExceptionLibProperties {
    private boolean enabled;

    @PostConstruct
    void init() {
        log.info("Exception lib initialized: {}", this);
    }

    @Override
    public String toString() {
        return "enabled=" + enabled;
    }
}
