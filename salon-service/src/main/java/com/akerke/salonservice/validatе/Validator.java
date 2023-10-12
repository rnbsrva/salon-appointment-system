package com.akerke.salonservice.validatе;

import com.akerke.salonservice.exception.InvalidRequestException;
import org.springframework.validation.BindingResult;

import java.util.Objects;
import java.util.function.Consumer;

public class Validator {

    public static void validate(BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            returnError.accept(bindingResult);
        }
    }

    private static Consumer<BindingResult> returnError = br -> {
        StringBuilder sb = new StringBuilder();
        br.getFieldErrors().forEach(error -> sb.append(error.getDefaultMessage()));
        throw new InvalidRequestException(Objects.requireNonNull(br.getTarget()).getClass(), sb.toString());
    };

}
