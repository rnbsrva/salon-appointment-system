package com.akerke.exceptionlib.exception;

public class OTPExpiredException extends RuntimeException{
    public OTPExpiredException(){
        super("life time one time exception expired");
    }
}
