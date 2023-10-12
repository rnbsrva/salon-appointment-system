package com.akerke.authservice.validate;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberValidatorTest {

    private PhoneNumberValidator phoneNumberValidator;

    @Mock private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        phoneNumberValidator = new PhoneNumberValidator();
    }

    @Test
    void isValid_whenValidPhoneNumber_thenReturnTrue() {
        var validPhoneNumber = "+12345678901";
        assertTrue(phoneNumberValidator.isValid(validPhoneNumber, context),
                "Expected isValid to return true for valid phone number");
    }

    @Test
    void isValid_whenInvalidPhoneNumber_thenReturnFalse() {
        var invalidPhoneNumber = "+1234567890";
        assertFalse(phoneNumberValidator.isValid(invalidPhoneNumber, context),
                "Expected isValid to return false for invalid phone number");
    }

    @Test
    void isValid_whenNullPhoneNumber_thenReturnFalse() {
        String nullPhoneNumber = null;
        assertFalse(phoneNumberValidator.isValid(nullPhoneNumber, context),
                "Expected isValid to return false for null phone number");
    }

    @Test
    void isValid_whenEmptyPhoneNumber_thenReturnFalse() {
        var emptyPhoneNumber = "";
        assertFalse(phoneNumberValidator.isValid(emptyPhoneNumber, context),
                "Expected isValid to return false for empty phone number");
    }
}