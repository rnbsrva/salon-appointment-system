package com.akerke.storageservice.common.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class FileOperationException extends RuntimeException {
    public FileOperationException(
            Exception e
    ){
        super(e.getMessage());
    }

    public FileOperationException(
        String msg
    ){
        super(msg);
    }

    @ExceptionHandler(FileOperationException.class)
    ProblemDetail handle(FileOperationException e) {
        return ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
    }
}
