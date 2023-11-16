package com.akerke.exceptionlib.exception;

public class InvalidOTPException extends RuntimeException {
    public InvalidOTPException() {
        super("invalid one time password");
    }
}
