package com.akerke.qrservice.service;

import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.CompletableFuture;

public interface QRService {

    String generateQR(HttpServletResponse response, String data);

    CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String data);

}
