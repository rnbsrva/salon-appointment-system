package com.akerke.qrservice.service.impl;

import com.akerke.qrservice.entity.QR;
import com.akerke.qrservice.repository.QRRepository;
import com.akerke.qrservice.service.QRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class QRServiceImpl implements QRService {

    private final QRRepository qrRepository;

    private static final String ATTACHMENT_SOURCE = "qr-image";

    @Value("${qr.charset}")
    private String charset;

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link) throws IOException {

        var optionalQR = qrRepository.findByLink(link);
        if (optionalQR.isPresent()) {

            var qr = optionalQR.get();

            return CompletableFuture.runAsync(() -> {
                try {
                    response.sendRedirect(createLinkToDownload(qr.getId(), qr.getId()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } else {
            return CompletableFuture.runAsync(() -> {
                try {
                    var bytes = generateQR(response, link);

                    var qr = new QR(link);
                    qrRepository.save(qr);

                    response.sendRedirect(createLinkToUpload(qr.getId(), qr.getId()));
                    log.info("generate qr code");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    @SneakyThrows
    public CompletableFuture<byte[]> generateQR(
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

        var outputStream = response.getOutputStream();

        var byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.writeTo(outputStream);

        var byteArray = byteArrayOutputStream.toByteArray();

        outputStream.flush();
        outputStream.close();

        return CompletableFuture.completedFuture(byteArray);
    }

    private String createLinkToDownload(String name, String target) {
        return "http://localhost:8080/storage/download?name=" + name + "&target=" + target + "&source=" + ATTACHMENT_SOURCE;
    }

    private String createLinkToUpload(String name, String target) {
        return "http://localhost:8080/storage/upload?name=" + name + "&target=" + target + "&source=" + ATTACHMENT_SOURCE;
    }
}
