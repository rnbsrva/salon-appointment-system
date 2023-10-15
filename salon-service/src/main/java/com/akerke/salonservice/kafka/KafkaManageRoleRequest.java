package com.akerke.salonservice.kafka;

public record KafkaManageRoleRequest(
        Long target,
        String email,
        Boolean add
) {
}