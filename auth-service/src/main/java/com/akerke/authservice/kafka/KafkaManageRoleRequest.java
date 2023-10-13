package com.akerke.authservice.kafka;

public record KafkaManageRoleRequest(
        Long target,
        String email,
        Boolean add
) {
}