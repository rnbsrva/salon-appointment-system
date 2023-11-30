package com.akerke.salonservice.infrastructure.kafka;

import com.akerke.salonservice.common.constants.NotificationType;

public record NotificationDTO (
        Long recipientId,
        String title,
        String message,
        NotificationType type,
        String phoneNumber
){
}
