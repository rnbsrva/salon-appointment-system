package com.akerke.authservice.validate;

import com.akerke.authservice.exception.InvalidRequestPayloadException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;


import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class BindingValidator {

    private static final Consumer<BindingResult> returnErrorToClient = bindingResult -> {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors()
                .forEach(error ->
                        sb
                                .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                                .append(".")
                                .append(System.lineSeparator())
                );
        throw new InvalidRequestPayloadException(sb.toString());
    };

    public static void validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("invalid request from client");
            returnErrorToClient.accept(bindingResult);
        }
    }
}
