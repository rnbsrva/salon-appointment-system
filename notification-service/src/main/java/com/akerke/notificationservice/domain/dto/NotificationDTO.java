package com.akerke.notificationservice.domain.dto;

import com.akerke.notificationservice.common.constansts.NotificationType;


public record NotificationDTO (
         Long recipientId,
         String title,
         String message,
         NotificationType type,
         String phoneNumber
){
}
