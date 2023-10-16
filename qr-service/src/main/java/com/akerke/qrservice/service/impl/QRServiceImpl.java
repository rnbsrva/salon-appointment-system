package com.akerke.qrservice.service.impl;

import com.akerke.qrservice.entity.QR;
import com.akerke.qrservice.feign.StorageServiceFeignClient;
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

    private static final String ATTACHMENT_SOURCE = "QR-IMAGE";

    @Value("${qr.charset}")
    private String charset;

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link) {

        var optionalQR = qrRepository.findByLink(link);
        if (optionalQR.isPresent()) {

            var qr = optionalQR.get();

            return CompletableFuture.runAsync(() -> {
                try {
                    log.info("redirect to storage service");
                    response.sendRedirect(createLinkToDownload(link,String.valueOf(1)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } else {
            return CompletableFuture.runAsync(() -> {
                try {

                    var qr = qrRepository.save(new QR(link));

                    generateMultipartQR(response, link)
                            .whenCompleteAsync((multipartFile, throwable) -> {
                                if (throwable != null) {
                                    // TODO: 10/16/2023  handle
                                } else {
                                    log.info("send req to storage service");
                                    this.feignClient.saveToStorage(
                                            multipartFile,
                                            1L, // todo
                                            link
                                    );
                                }
                            });
                    log.info("generate qr code");
                } catch (Exception e) {
                    throw new RuntimeException(e);
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

        return CompletableFuture.completedFuture(new ByteArrayMultipartFile(byteArrayOutputStream.toByteArray(), data, data, "image/png"));
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

    private MultipartFile createMultipartFile(ByteArrayOutputStream byteArrayOutputStream, String fileName) {
        byte[] fileBytes = byteArrayOutputStream.toByteArray();

        return new ByteArrayMultipartFile(fileBytes, fileName, fileName, "image/png");
    }


    public class ByteArrayMultipartFile implements MultipartFile {

        private final byte[] content;
        private final String name;
        private final String originalFilename;
        private final String contentType;

        public ByteArrayMultipartFile(byte[] content, String name, String originalFilename, String contentType) {
            this.content = content;
            this.name = name;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(content);
        }
    }

}
