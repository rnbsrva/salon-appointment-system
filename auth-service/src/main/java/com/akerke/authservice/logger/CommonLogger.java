package com.akerke.authservice.logger;

import com.akerke.authservice.reflection.MapUtils;
import com.akerke.authservice.utils.LogBody;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class CommonLogger {

    private void addProperty(String propertyName, String propertyValue) {
        String existingValue = MDC.get(propertyName);
        if (existingValue != null) {
            log.error("Property " + propertyName + " was init in this session log. " +
                    "MDC context already has a value with " + propertyName);
        } else {
            MDC.put(propertyName, propertyValue);
        }
    }

    private void addAdditionalLogProperties(LogBody additionalLogProperties) {
        var map = MapUtils.classToMapWithValueString(additionalLogProperties);
        map.forEach((k, v) -> {
            addProperty(k, v != null ? v : "null");
        });
    }

    public void trace(String message, LogBody additionalLogProperties) {
        addAdditionalLogProperties(additionalLogProperties);
        log.trace(message);
        dropSession();
    }

    public void debug(String message, LogBody additionalLogProperties) {
        addAdditionalLogProperties(additionalLogProperties);
        log.debug(message);
        dropSession();
    }

    public void info(String message, LogBody additionalLogProperties) {
        addAdditionalLogProperties(additionalLogProperties);
        log.info(message);
        dropSession();
    }

    public void warn(String message, LogBody additionalLogProperties) {
        addAdditionalLogProperties(additionalLogProperties);
        log.warn(message);
        dropSession();
    }

    public void error(String message, LogBody additionalLogProperties) {
        addAdditionalLogProperties(additionalLogProperties);
        log.error(message);
        dropSession();
    }

    public void dropSession() {
        MDC.clear();
    }
}
