package com.akerke.authservice.exception;

import jakarta.validation.constraints.Email;

public class EmailRegisteredYetException extends RuntimeException {
    public EmailRegisteredYetException(@Email String email) {
        super("email %s registered yet".formatted(email));
    }
}
