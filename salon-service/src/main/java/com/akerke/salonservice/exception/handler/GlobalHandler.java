package com.akerke.salonservice.exception.handler;

import com.akerke.salonservice.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.function.BiFunction;

@RestControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    ProblemDetail handleInvalidRequestException(InvalidRequestException e) {
        return  withDetails.apply(e, HttpStatus.BAD_REQUEST);
    }

    private final BiFunction<RuntimeException, HttpStatus, ProblemDetail> withDetails =
            (e, status) ->
                    ProblemDetail.forStatusAndDetail(status, e.getMessage());
}