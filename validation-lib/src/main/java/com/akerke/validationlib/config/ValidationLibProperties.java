package com.akerke.validationlib.config;


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
@ConfigurationProperties("app.validation.lib")
public class ValidationLibProperties {
    private boolean enabled;

    @PostConstruct
    void init() {
        log.info("ValidationLibProperties : {}", this);
    }

    @Override
    public String toString() {
        return "enabled=" + enabled;
    }
}
