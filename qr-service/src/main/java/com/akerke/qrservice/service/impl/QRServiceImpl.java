package com.akerke.qrservice.service.impl;

import com.akerke.qrservice.service.QRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class QRServiceImpl implements QRService {


    @Value("${qr.charset}")
    private String charset;

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String data) {
        try {
            generateQR(response, data);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    @SneakyThrows
    public void generateQR(HttpServletResponse response, String qrURL) {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(qrURL.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, 400, 400);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "inline; filename=qr-code.png");

        OutputStream outputStream = response.getOutputStream();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.writeTo(outputStream);

        outputStream.flush();
        outputStream.close();
    }
}
