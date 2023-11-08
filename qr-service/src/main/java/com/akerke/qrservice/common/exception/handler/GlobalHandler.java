package com.akerke.qrservice.common.exception.handler;

import com.akerke.qrservice.common.exception.QRGenerationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.BiFunction;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(QRGenerationException.class)
    ProblemDetail handleInvalidRequestException(QRGenerationException e) {
        return withDetails.apply(e, HttpStatus.BAD_REQUEST);
    }

    private final BiFunction<RuntimeException, HttpStatus, ProblemDetail> withDetails =
            (e, status) -> ProblemDetail.forStatusAndDetail(status, e.getMessage());
}
