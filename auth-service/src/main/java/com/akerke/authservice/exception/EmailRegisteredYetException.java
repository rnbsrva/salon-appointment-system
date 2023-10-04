package com.akerke.authservice.exception;

public class EmailRegisteredYetException extends RuntimeException {
    public EmailRegisteredYetException( String email) {
        super("email %s registered yet".formatted(email));
    }
}
