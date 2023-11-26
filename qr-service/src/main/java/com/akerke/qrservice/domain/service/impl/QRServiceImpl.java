package com.akerke.qrservice.domain.service.impl;

import com.akerke.qrservice.domain.service.QRService;
import com.akerke.qrservice.common.utils.ByteArrayMultipartFile;
import com.akerke.qrservice.common.exception.QRGenerationException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class QRServiceImpl implements QRService {

    @Value("${qr.charset}")
    private String charset;

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link) {
        return CompletableFuture.runAsync(() -> {
            try {
                generateMultipartQR(response, link)
                        .whenCompleteAsync((multipartFile, e) -> {
                            if (e != null) {
                                throw new QRGenerationException(link, e);
                            } else {
                                log.info("generate qr code data[{}]", link);
                            }
                        });
            } catch (Exception e) {
                throw new QRGenerationException(link, e);
            }
        });
    }

    @Override
    public CompletableFuture<MultipartFile> generateMultipartQR(
            HttpServletResponse response,
            String data
    ) throws Exception {
        var matrix = new MultiFormatWriter()
                .encode(
                        new String(data.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 600, 600
                );

        var bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "inline; filename=qr-code.png");

        var outputStream = response.getOutputStream();

        var byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.writeTo(outputStream);

        outputStream.flush();
        outputStream.close();

        return CompletableFuture.completedFuture(
                new ByteArrayMultipartFile(
                        byteArrayOutputStream.toByteArray(),
                        data,
                        data,
                        MediaType.MULTIPART_FORM_DATA
                )
        );
    }

}
