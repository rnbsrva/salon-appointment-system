package com.akerke.authservice.validate;

import com.akerke.authservice.common.exception.InvalidRequestPayloadException;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;

import java.util.function.Consumer;

/**
 * A utility class for validating and handling Spring MVC binding results.
 */
@Slf4j
@UtilityClass
public class BindingValidator {

    /**
     * A consumer function that throws an exception with detailed error messages for a given binding result.
     *
     * @param bindingResult The Spring MVC binding result containing validation errors.
     * @throws InvalidRequestPayloadException If the binding result has errors, an exception with error details is thrown.
     */
    private static final Consumer<BindingResult> returnErrorToClient = br -> {
        var sb = new StringBuilder();

        br.getFieldErrors()
                .forEach(error ->
                        sb
                                .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                                .append(".")
                                .append(System.lineSeparator())
                );

        throw new InvalidRequestPayloadException(sb.toString());
    };

    /**
     * Validates a request's binding result and throws an exception with error details if validation fails.
     *
     * @param br The Spring MVC binding result to validate.
     */
    public static void validateRequest(BindingResult br) {
        if (br.hasErrors()) {
            log.error("Invalid request from client" );
            returnErrorToClient.accept(br);
        }
    }
}

