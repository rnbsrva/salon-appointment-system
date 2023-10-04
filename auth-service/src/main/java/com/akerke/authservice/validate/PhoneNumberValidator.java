package com.akerke.authservice.validate;

import com.akerke.authservice.annotations.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(
            String phoneNumber,
            ConstraintValidatorContext ctx
    ) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        final var PHONE_NUMBER_PATTERN = "^[0-9]{11}$";

        return Pattern.matches(PHONE_NUMBER_PATTERN, phoneNumber);
    }

}
