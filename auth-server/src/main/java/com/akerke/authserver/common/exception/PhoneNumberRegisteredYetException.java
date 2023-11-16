package com.akerke.authserver.common.exception;

/**
 * Exception thrown when a phone number is already registered.
 */
public class PhoneNumberRegisteredYetException extends RuntimeException {

    /**
     * Constructs a new PhoneNumberRegisteredYetException with the specified phone number.
     *
     * @param phoneNumber the phone number that is already registered
     */
    public PhoneNumberRegisteredYetException(String phoneNumber) {
        super("phone number %s registered yet".formatted(phoneNumber));
    }
}