package com.akerke.salonservice.validatе;

import com.akerke.salonservice.common.validate.Validator;
import com.akerke.salonservice.common.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidatorTest {

    private BindingResult br;

    @BeforeEach
    void setUp() {
        br = mock(BindingResult.class);
    }

    @Test
    void validate_shouldNoException_whenRequestWithNoErrors() {
        when(br.hasErrors()).thenReturn(false);

        assertDoesNotThrow(() -> Validator.validate(br));
    }

    @Test
    void validate_shouldThrowException_whenExceptionWithErrors() {

        when(br.hasErrors()).thenReturn(true);
        when(br.getTarget()).thenReturn(Object.class);

        var fieldError1 = new FieldError("objectName", "fieldName1", "Error message 1");
        var fieldError2 = new FieldError("objectName", "fieldName2", "Error message 2");

        when(br.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        var exception = assertThrows(InvalidRequestException.class, () -> {
            Validator.validate(br);
        });

        var expectedMessage = "Invalid request for Class. Error: Error message 1." + System.lineSeparator() + "Error message 2." + System.lineSeparator();
        assertEquals(expectedMessage, exception.getMessage());
    }

}

