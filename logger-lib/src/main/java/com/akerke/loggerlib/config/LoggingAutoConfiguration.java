package com.akerke.loggerlib.config;

import com.akerke.loggerlib.aspects.ControllerTimeExecutionAspect;
import com.akerke.loggerlib.logger.CommonLogger;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@ConditionalOnProperty(prefix = "app.logger.lib", name = "enabled", havingValue = "true")
public class LoggingAutoConfiguration {


    @PostConstruct
    void init() {
        log.info("LoggingAutoConfiguration initialized ");
    }

    @Bean
    public CommonLogger commonLogger() {
        return new CommonLogger();
    }

    @Bean
    public ControllerTimeExecutionAspect controllerTimeExecutionAspect(
            CommonLogger commonLogger
    ) {
        return new ControllerTimeExecutionAspect(commonLogger);
    }
}
