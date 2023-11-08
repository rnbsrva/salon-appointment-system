package com.akerke.qrservice.common.exception;

public class QRGenerationException extends RuntimeException{

    public QRGenerationException(String link) {
        super("QR with link: %s cannot be generated".formatted(link));
    }
}
