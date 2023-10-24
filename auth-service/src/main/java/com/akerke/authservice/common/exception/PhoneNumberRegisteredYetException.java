package com.akerke.authservice.common.exception;

public class PhoneNumberRegisteredYetException extends RuntimeException {
    public PhoneNumberRegisteredYetException(String phoneNumber) {
        super("phone number %s registered yet".formatted(phoneNumber));
    }
}
