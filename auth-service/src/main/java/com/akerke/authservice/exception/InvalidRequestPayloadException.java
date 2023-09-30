package com.akerke.authservice.exception;

public class InvalidRequestPayloadException extends RuntimeException {
    public InvalidRequestPayloadException(String e) {
        super(e);
    }
}
