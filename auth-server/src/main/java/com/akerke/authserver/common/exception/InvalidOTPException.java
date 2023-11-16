package com.akerke.authserver.common.exception;

public class InvalidOTPException extends RuntimeException {
    public InvalidOTPException() {
        super("invalid one time password");
    }
}
