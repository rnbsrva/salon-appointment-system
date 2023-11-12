package com.akerke.authservice.infrastructure.kafka;

public record KafkaManageRoleRequest(
        Long target,
        String email,
        Boolean add
) {
}