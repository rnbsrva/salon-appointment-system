package com.akerke.authservice.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaEmailMessageDetails {
    private String recipient;
    private String msgBody;
    private String subject;
}