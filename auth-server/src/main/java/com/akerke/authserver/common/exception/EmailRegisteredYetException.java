package com.akerke.authserver.common.exception;

/**
 * Exception thrown when an email is already registered.
 */
public class EmailRegisteredYetException extends RuntimeException {

    /**
     * Constructs a new EmailRegisteredYetException with the specified email.
     *
     * @param email the email that is already registered
     */
    public EmailRegisteredYetException(String email) {
        super("email %s registered yet".formatted(email));
    }
}