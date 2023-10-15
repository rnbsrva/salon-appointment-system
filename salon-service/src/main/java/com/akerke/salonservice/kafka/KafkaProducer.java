package com.akerke.salonservice.kafka;

public interface KafkaProducer {

    <T> void produce(String topic, T data);

}
