package com.akerke.salonservice.infrastructure.kafka;

public record KafkaManageRoleRequest(
        Long target,
        String email,
        Boolean add
) {
}