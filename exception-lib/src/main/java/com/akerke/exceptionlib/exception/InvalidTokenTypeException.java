package com.akerke.exceptionlib.exception;

public class InvalidTokenTypeException extends RuntimeException {
    public InvalidTokenTypeException(){
        throw new InvalidCredentialsException();
    }
}
