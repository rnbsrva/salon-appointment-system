package com.akerke.qrservice.domain.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface QRService {

    CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String data) throws IOException;

    CompletableFuture<MultipartFile> generateMultipartQR(HttpServletResponse response, String date) throws Exception;
}
