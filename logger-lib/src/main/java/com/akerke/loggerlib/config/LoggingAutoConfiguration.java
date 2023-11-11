package com.akerke.loggerlib.config;

import com.akerke.loggerlib.common.aspect.EnableLoggerLibTimeExecutionAspect;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@EnableAspectJAutoProxy
@ConditionalOnProperty(
        prefix = "app.logger.lib",
        name = "enabled",
        havingValue = "true"
)
public class LoggingAutoConfiguration {

    @PostConstruct
    void init() {
        log.info("LoggingAutoConfiguration initialized ");
    }

    @Bean
    @ConditionalOnClass(EnableLoggerLibTimeExecutionAspect.class)
    public EnableLoggerLibTimeExecutionAspect controllerTimeExecutionAspect(
            LoggingProperties loggingProperties
    ) {
        log.info("Creating EnableLoggerLibTimeExecutionAspect instance");
        return new EnableLoggerLibTimeExecutionAspect(loggingProperties.getName());
    }

}
