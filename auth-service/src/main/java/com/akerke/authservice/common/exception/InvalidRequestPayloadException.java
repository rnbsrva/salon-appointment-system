package com.akerke.authservice.common.exception;

public class InvalidRequestPayloadException extends RuntimeException {
    public InvalidRequestPayloadException(String e) {
        super(e);
    }
}
