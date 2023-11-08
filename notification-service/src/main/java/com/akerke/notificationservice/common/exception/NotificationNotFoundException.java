package com.akerke.notificationservice.common.exception;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(Long id) {
        super("notification by id: %d not found".formatted(id));
    }
}
