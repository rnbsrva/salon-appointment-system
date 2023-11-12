package com.akerke.authservice.infrastructure.kafka;

public interface KafkaProducer {

    <T> void produce(String topic, T data);

}
