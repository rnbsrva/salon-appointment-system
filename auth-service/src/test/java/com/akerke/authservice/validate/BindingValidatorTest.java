package com.akerke.authservice.validate;

import com.akerke.authservice.common.exception.InvalidRequestPayloadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BindingValidatorTest {

    private BindingResult br;

    @BeforeEach
    void setUp() {
        br = mock(BindingResult.class);
    }

    @Test
    void validateRequest_shouldNoException_whenRequestWithNoErrors(){
        when(br.hasErrors()).thenReturn(false);

        assertDoesNotThrow(() -> BindingValidator.validateRequest(br));
    }

    @Test
    void validateRequest_shouldThrowException_whenExceptionWithErrors(){

        when(br.hasErrors()).thenReturn(true);

        var fieldError1 = new FieldError("objectName", "fieldName1", "Error message 1");
        var fieldError2 = new FieldError("objectName", "fieldName2", "Error message 2");

        when(br.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        var exception = assertThrows(InvalidRequestPayloadException.class, () -> {
            BindingValidator.validateRequest(br);
        });

        var expectedMessage = "Error message 1." + System.lineSeparator() + "Error message 2." + System.lineSeparator();
        assertEquals(expectedMessage, exception.getMessage());
    }

}