package com.akerke.qrservice.controller;

import com.akerke.qrservice.service.QRService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QRController {

    private final QRService qrService;

    @SneakyThrows
    @GetMapping
    void getQR(
            HttpServletResponse servletResponse,
            @RequestParam String data
    ){
        qrService.generateQR(servletResponse, data);
    }

}
