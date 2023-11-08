package com.akerke.qrservice.controller;

import com.akerke.qrservice.domain.service.QRService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Api
public class QRController {

    private final QRService qrService;

    @GetMapping("generate")
    @ApiOperation("Generate a new QR by given link")
    public CompletableFuture<Void> generateQR(
            HttpServletResponse response,
            @RequestParam String data
    ) throws IOException {
        return qrService.generateQRAsync(response, data);
    }

}
