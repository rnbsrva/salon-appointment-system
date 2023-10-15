package com.akerke.qrservice.service.impl;

import com.akerke.qrservice.entity.QR;
import com.akerke.qrservice.repository.QRRepository;
import com.akerke.qrservice.service.QRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class QRServiceImpl implements QRService {

    private final QRRepository qrRepository;

    @Value("${qr.charset}")
    private String charset;

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String data) {

        var optionalQR = qrRepository.findByLink(data);
        if (optionalQR.isPresent()) {

            var qr = optionalQR.get();
            byte[] bytes = Base64.decodeBase64(qr.getBase64Data());

            response.setContentType("application/octet-stream");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"downloaded_file.bin\"");

            return CompletableFuture.runAsync(() -> {
                try {
                    log.info("return existing qr code");
                    response.getOutputStream().write(bytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } else {
            return CompletableFuture.runAsync(() -> {
                try {
                    var base64Data = generateQR(response, data);
                    var qr = new QR(data, base64Data);
                    qrRepository.save(qr);
                    log.info("generate qr code");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    @SneakyThrows
    public String generateQR(
            HttpServletResponse response,
            String qrURL
    ) {
        var matrix = new MultiFormatWriter()
                .encode(
                        new String(qrURL.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 600, 600
                );

        var bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "inline; filename=qr-code.png");

        OutputStream outputStream = response.getOutputStream();

        var byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.writeTo(outputStream);

        var byteArray = byteArrayOutputStream.toByteArray();
        var base64Image = Base64.encodeBase64String(byteArray);

        outputStream.flush();
        outputStream.close();

        return base64Image;
    }
}
