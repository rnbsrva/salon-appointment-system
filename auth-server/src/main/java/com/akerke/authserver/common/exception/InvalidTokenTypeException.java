package com.akerke.authserver.common.exception;
public class InvalidTokenTypeException extends RuntimeException {
    public InvalidTokenTypeException(){
        throw new InvalidCredentialsException();
    }
}