package com.akerke.storageservice.exception;

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
}
