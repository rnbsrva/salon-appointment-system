package com.akerke.salonservice.kafka;

public interface KafkaProducer {

    /**
     * Produce a message to the specified Kafka topic.
     *
     * @param topic The name of the Kafka topic.
     * @param data  The data to be sent to the Kafka topic.
     */
    <T> void produce(String topic, T data);

}
