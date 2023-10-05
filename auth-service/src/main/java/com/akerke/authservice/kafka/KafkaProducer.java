package com.akerke.authservice.kafka;

public interface KafkaProducer {

    <T> void produce(String topic, T data);

}
