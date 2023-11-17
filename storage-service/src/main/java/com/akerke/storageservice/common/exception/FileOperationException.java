package com.akerke.storageservice.common.exception;
/**
 * Exception thrown if an exception occurs during file operations with Minio.
 */
public class FileOperationException extends RuntimeException{

    /**
     * Constructs a new FileOperationException with the specified exception.
     *
     * @param e the exception that occurred during file operations
     */
    public FileOperationException(Exception e) {
        super(e.getMessage());
    }

    /**
     * Constructs a new FileOperationException with the specified detail message.
     *
     * @param msg the detail message
     */
    public FileOperationException(String msg) {
        super(msg);
    }
}
