package com.akerke.authserver.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class ApacheKafkaConfig {

    @Bean
    ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducerTemplate(
            KafkaProperties properties
    ) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<String, Object>(SenderOptions.create(props));
    }

}
