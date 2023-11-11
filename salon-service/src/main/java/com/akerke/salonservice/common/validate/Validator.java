package com.akerke.salonservice.common.validate;

import com.akerke.exceptionlib.exception.InvalidRequestPayloadException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.function.Consumer;

/**
 * A utility class for validating and handling Spring MVC binding results.
 */
@Slf4j
@UtilityClass
public class Validator {

    private static final Consumer<BindingResult> returnErrorToClient = br -> {

        if (br.getTarget() == null) {
            return;
        }

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
    public static void validate(BindingResult br) {
        if (br.hasErrors()) {
            log.error("Invalid request from client");
            returnErrorToClient.accept(br);
        }
    }
}

