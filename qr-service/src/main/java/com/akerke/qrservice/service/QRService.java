package com.akerke.qrservice.service;

import jakarta.servlet.http.HttpServletResponse;

public interface QRService {

    void generateQR(HttpServletResponse response, String data);


}
