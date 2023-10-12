package com.akerke.authservice.validate;

import com.akerke.authservice.annotations.Password;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;
    @Mock private ConstraintValidatorContext context;
    private Password password;

    @BeforeEach
    public void setUp() {
        passwordValidator = new PasswordValidator();
        passwordValidator.initialize(new Password() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public int minLength() {
                return 8;
            }

            @Override
            public boolean requireUppercase() {
                return true;
            }

            @Override
            public boolean requireLowercase() {
                return true;
            }

            @Override
            public boolean requireDigit() {
                return true;
            }

            @Override
            public boolean requireSpecialChar() {
                return true;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class[] payload() {
                return new Class[0];
            }
        });
    }

    @Test
    public void isValid_whenPasswordIsValid_thenReturnTrue() {
        assertTrue(passwordValidator.isValid("ValidPassword1!", context));
    }

    @Test
    public void isValid_whenPasswordIsNull_thenReturnFalse() {
        assertFalse(passwordValidator.isValid(null, context));
    }

    @Test
    public void isValid_whenPasswordLengthIsLessThanMinLength_thenReturnFalse() {
        assertFalse(passwordValidator.isValid("Short1!", context));
    }

    @Test
    public void isValid_whenPasswordDoesNotContainUppercase_thenReturnFalse() {
        assertFalse(passwordValidator.isValid("lowercase1!", context));
    }

    @Test
    public void isValid_whenPasswordDoesNotContainLowercase_thenReturnFalse() {
        assertFalse(passwordValidator.isValid("UPPERCASE1!", context));
    }

    @Test
    public void isValid_whenPasswordDoesNotContainDigit_thenReturnFalse() {
        assertFalse(passwordValidator.isValid("NoDigit!", context));
    }
}