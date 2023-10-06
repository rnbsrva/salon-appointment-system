package com.akerke.notificationservice.exception;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(Long id) {
        super("notification by id: %d not found".formatted(id));
    }
}
