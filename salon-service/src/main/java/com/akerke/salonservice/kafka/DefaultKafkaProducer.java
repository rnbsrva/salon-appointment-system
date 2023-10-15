package com.akerke.salonservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * This class implements a Kafka producer using a KafkaTemplate.
 * It provides a method to produce messages to a Kafka topic.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DefaultKafkaProducer implements KafkaProducer {

    private final KafkaTemplate<String, Object> kafka;

    /**
     * Produce a message to the specified Kafka topic.
     *
     * @param topic The name of the Kafka topic.
     * @param data  The data to be sent to the Kafka topic.
     */
    @Override
    public <T> void produce(String topic, T data) {
        kafka.send(topic, data)
                .whenCompleteAsync((res, th) -> {
                            if (th != null) {
                                log.error("kafka produced message error {}", th.getMessage());
                            } else {
                                log.info("{}, topic: {}", res.getProducerRecord(), res.getProducerRecord().topic());
                            }
                        }
                );
    }

}
