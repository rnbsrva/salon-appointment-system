package com.akerke.notificationservice.config;

import io.github.resilience4j.springboot3.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class DefaultCircuitBreakerPropertiesConfiguration implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(
            @NonNull Object bean,
            @NonNull String beanName
    ) throws BeansException {
        if (bean instanceof CircuitBreakerProperties circuitBreakerProperties) {
            circuitBreakerProperties.getInstances()
                    .values()
                    .stream()
                    .filter(instanceProperties -> Objects.isNull(instanceProperties.getBaseConfig()))
                    .forEach(instanceProperties -> instanceProperties.setBaseConfig("default"));
        }
        return bean;
    }
}
