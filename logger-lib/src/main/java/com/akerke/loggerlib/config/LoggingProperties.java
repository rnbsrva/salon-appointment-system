package com.akerke.loggerlib.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Slf4j
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.logger.lib")
public class LoggingProperties {
    private boolean enabled;
    private String level;
    private String name;

    @PostConstruct
    void init() {
        log.info("Logging properties initialized: {}", this);
    }
}
