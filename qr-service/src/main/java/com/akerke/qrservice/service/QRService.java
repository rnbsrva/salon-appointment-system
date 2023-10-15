package com.akerke.qrservice.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface QRService {

    CompletableFuture<byte[]> generateQR(HttpServletResponse response, String data);

    CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String data) throws IOException;

}
