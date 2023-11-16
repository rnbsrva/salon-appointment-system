package com.akerke.exceptionlib.exception;

public class EmailNotVerified extends RuntimeException{
    public EmailNotVerified(){
        super("email not verified");
    }
}
