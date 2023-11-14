package com.akerke.validationlib.config;

import com.akerke.validationlib.aspect.ValidationAspect;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@EnableConfigurationProperties(ValidationLibProperties.class)
@ConditionalOnClass(ValidationLibProperties.class)
@EnableAspectJAutoProxy
@ConditionalOnProperty(
        prefix = "app.validation.lib",
        name = "enabled",
        havingValue = "true"
)
public class ValidationLibAutoConfiguration {

    @PostConstruct
    void init() {
        log.info("ValidationLibAuthConfiguration initialized ");
    }

    @Bean
    public ValidationAspect validationAspect(){
        log.info("ValidationAspect initialized");
        return new ValidationAspect();
    }


}
