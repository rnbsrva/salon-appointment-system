package com.akerke.storageservice.exception.handler;

import com.akerke.storageservice.dto.FileOperationDTO;
import com.akerke.storageservice.exception.BucketInitializerException;
import com.akerke.storageservice.exception.FileOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.function.BiFunction;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileOperationException.class)
    ProblemDetail handle(FileOperationException e) {
        return withDetails.apply(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(BucketInitializerException.class)
    ProblemDetail handle(BucketInitializerException e) {
        return withDetails.apply(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private final BiFunction<HttpStatus, RuntimeException, ProblemDetail>
            withDetails = (s, e) -> ProblemDetail.forStatusAndDetail(s, e.getMessage());

}
