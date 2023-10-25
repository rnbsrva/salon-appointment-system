package com.akerke.qrservice.service.impl;

import com.akerke.qrservice.entity.QR;
import com.akerke.qrservice.exception.QRGenerationException;
import com.akerke.qrservice.feign.StorageServiceFeignClient;
import com.akerke.qrservice.repository.QRRepository;
import com.akerke.qrservice.service.QRService;
import com.akerke.qrservice.utils.ByteArrayMultipartFile;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class QRServiceImpl implements QRService {

    private final QRRepository qrRepository;
    private final StorageServiceFeignClient feignClient;

    private static final String ATTACHMENT_SOURCE = "QR_IMAGE";

    @Value("${qr.charset}")
    private String charset;

    @Value("${spring.cloud.openfeign.client.config.storage-service.url}")
    private String storageUrl;


    @Async("asyncExecutor")
    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link) {

        var optionalQR = qrRepository.findByLink(link);
        if (optionalQR.isPresent()) {

            return CompletableFuture.runAsync(() -> {
                try {
                    log.info("redirect to storage service");
                    response.sendRedirect(createLinkToDownload(link));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } else {
            return CompletableFuture.runAsync(() -> {
                try {

                    var qr = qrRepository.save(new QR(link));

                    generateMultipartQR(response, link)
                            .whenCompleteAsync((multipartFile, e) -> {
                                if (e != null) {
                                    throw new QRGenerationException(link);
                                } else {
                                    log.info("send req to storage service");
                                    this.feignClient.saveToStorage(
                                            multipartFile,
                                            link
                                    );
                                }
                            });
                    log.info("generate qr code");
                } catch (Exception e) {
                    throw new QRGenerationException(link);
                }
            });
        }
    }

    @Override
    @SneakyThrows
    public CompletableFuture<MultipartFile> generateMultipartQR(HttpServletResponse response, String data) {
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

        return CompletableFuture.completedFuture(new ByteArrayMultipartFile(byteArrayOutputStream.toByteArray(), data, data, MediaType.MULTIPART_FORM_DATA));
    }

    public String createLinkToDownload(String name) {
        return storageUrl+"downloadQR?name=" + name + "&source=" + ATTACHMENT_SOURCE;
    }

}
