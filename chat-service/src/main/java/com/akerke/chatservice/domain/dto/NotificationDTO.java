package com.akerke.chatservice.domain.dto;

public record NotificationDTO(
         Long recipientId,
         String title,
         String message,
         String type,
         String phoneNumber
){
}
