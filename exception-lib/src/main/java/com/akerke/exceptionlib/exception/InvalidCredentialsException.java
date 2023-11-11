package com.akerke.exceptionlib.exception;


/**
 * Exception thrown when invalid credentials are provided.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new InvalidCredentialsException with the default error message.
     */
    public InvalidCredentialsException() {
        super("invalid credentials");
    }
}