package com.akerke.notificationservice.dto;

import com.akerke.notificationservice.constansts.NotificationType;


public record NotificationDTO (
         Long recipientId,
         String title,
         String message,
         NotificationType type,
         String phoneNumber
){
}
