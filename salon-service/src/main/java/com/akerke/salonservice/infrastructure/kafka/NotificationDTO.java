package com.akerke.salonservice.infrastructure.kafka;

public record NotificationDTO (
        Long recipientId,
        String title,
        String message,
        com.akerke.salonservice.common.constants.NotificationType type,
        String phoneNumber
){
}
