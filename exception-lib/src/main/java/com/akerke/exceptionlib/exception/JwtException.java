package com.akerke.exceptionlib.exception;

public class JwtException extends RuntimeException{
    public JwtException (Exception e){
        super(e.getMessage());
    }
}
