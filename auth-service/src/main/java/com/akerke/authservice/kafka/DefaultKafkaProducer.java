package com.akerke.authservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DefaultKafkaProducer implements KafkaProducer {

    private final KafkaTemplate<String, Object> kafka;

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
