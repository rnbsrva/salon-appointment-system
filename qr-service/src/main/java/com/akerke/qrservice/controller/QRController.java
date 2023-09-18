package com.akerke.qrservice.controller;

import com.akerke.qrservice.service.QRService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class QRController {

    private final QRService qrService;

    @GetMapping()
    public CompletableFuture<Void> generateQR(HttpServletResponse response,
                                              @RequestParam String data) throws Exception {
        return qrService.generateQRAsync(response, data);
    }

}
