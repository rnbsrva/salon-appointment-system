package com.akerke.authservice.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Class<?> c) {
        super("%s not found".formatted(c.getSimpleName()));
    }
}
