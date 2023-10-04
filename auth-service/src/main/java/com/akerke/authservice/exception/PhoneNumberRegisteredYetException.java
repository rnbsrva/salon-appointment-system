package com.akerke.authservice.exception;

public class PhoneNumberRegisteredYetException extends RuntimeException {
    public PhoneNumberRegisteredYetException(String phoneNumber) {
        super("phone number %s registered yet".formatted(phoneNumber));
    }
}
