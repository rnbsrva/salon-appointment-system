package com.akerke.authservice.validate;

import com.akerke.authservice.common.annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    private int minLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecialChar;

    @Override
    public void initialize(Password constraint) {
        minLength = constraint.minLength();
        requireUppercase = constraint.requireUppercase();
        requireLowercase = constraint.requireLowercase();
        requireDigit = constraint.requireDigit();
        requireSpecialChar = constraint.requireSpecialChar();
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        if (value == null) {
            return false;
        }

        if (value.length() < minLength) {
            return false;
        }

        if (requireUppercase && !Pattern.compile("[A-Z]").matcher(value).find()) {
            return false;
        }

        if (requireLowercase && !Pattern.compile("[a-z]").matcher(value).find()) {
            return false;
        }

        if (requireDigit && !Pattern.compile("[0-9]").matcher(value).find()) {
            return false;
        }

        return !requireSpecialChar || Pattern.compile("[!@#$%^&*()-_=+]").matcher(value).find();
    }

}
