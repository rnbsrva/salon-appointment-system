package com.akerke.authservice.infrastructure.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaEmailMessageDetails {
    private String recipient;
    private String msgBody;
    private String subject;
}