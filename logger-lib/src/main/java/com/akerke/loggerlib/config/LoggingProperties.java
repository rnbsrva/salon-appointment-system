package com.akerke.loggerlib.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "enabled=" + enabled +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' ;
    }
}
