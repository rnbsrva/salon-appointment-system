package com.akerke.authserver.common.exception;

public class EmailNotVerified extends RuntimeException{
    public EmailNotVerified(){
        super("email not verified");
    }
}