package com.akerke.storageservice.domain.service.impl;

import com.akerke.storageservice.common.constants.AttachmentSource;
import com.akerke.storageservice.domain.dto.FileOperationDTO;
import com.akerke.storageservice.common.exception.FileOperationException;
import com.akerke.storageservice.domain.service.MinIOService;
import io.minio.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinIOServiceImpl implements MinIOService {

    private final MinioClient minioClient;
    private final AsyncTaskExecutor applicationTaskExecutor;

    private static final Integer TIME_LIMIT = 3;

    @Value("${spring.minio.download-link-prefix}")
    private String urlPrefix;

    @Override
    public void putObject(FileOperationDTO dto, MultipartFile file) {
        this.getFromFuture(submit(() -> {
            try {
                var in = new ByteArrayInputStream(file.getBytes());
                var objectName = file.getOriginalFilename();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(dto.source().getName())
                                .object(dto.target().toString().concat("/").concat(objectName))
                                .stream(in, -1, 10485760).build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }
        }));
    }

    @Override
    public void putQR(FileOperationDTO dto, MultipartFile file) {
        log.info("new qr saved: "+dto.toString());
        log.info(file.getContentType());
        this.getFromFuture(submit(() -> {
            try {
                var in = new ByteArrayInputStream(file.getBytes());
                var objectName = file.getOriginalFilename();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .contentType(MediaType.IMAGE_PNG_VALUE)
                                .bucket(dto.source().getName())
                                .object(objectName)
                                .stream(in, -1, 10485760).build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }
        }));
    }

    @Override
    public void getQR(FileOperationDTO dto, HttpServletResponse response) {
        this.getFromFuture(submit(() -> {
            GetObjectResponse minioInputStream;
            try {
                minioInputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(dto.source().getName())
                                .object(dto.name())
                                .build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }

            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            response.setHeader("Content-Disposition", "inline; filename=qr-code.png");

            try (
                    var inputStream = minioInputStream;
                    var outputStream = response.getOutputStream()
            ) {

                var buffer = new byte[10240];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

            } catch (Exception e) {
                throw new RuntimeException();
            }
        }));
    }

    @Override
    @SneakyThrows
    public void getObject(FileOperationDTO dto, HttpServletResponse response) {
        this.getFromFuture(submit(() -> {
            GetObjectResponse minioInputStream;
            try {
                minioInputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(dto.source().getName())
                                .object(toObjectNameWithFolder(dto))
                                .build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + dto.name() + "\"");

            try (
                    var inputStream = minioInputStream;
                    var outputStream = response.getOutputStream()
            ) {

                var buffer = new byte[10240];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

            } catch (Exception e) {
                throw new RuntimeException();
            }
        }));
    }

    @Override
    @SneakyThrows
    public void removeBucket(Long target, AttachmentSource source) {

        this.getFromFuture(submit(() -> {

            var bucketName = toBucketName(source, target);

            try {
                minioClient.removeBucket(
                        RemoveBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }
        }));

    }

    @Override
    @SneakyThrows
    public void removeObject(FileOperationDTO dto) {
        this.getFromFuture(submit(() -> {
            try {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(dto.source().getName())
                                .object(toObjectNameWithFolder(dto))
                                .build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }
        }));
    }

    @Override
    @SneakyThrows
    public void getObjects(Long target, AttachmentSource source, HttpServletResponse response) {
        this.getFromFuture(submit(() -> {
                            var minioInputStream = minioClient.listObjects(
                                    ListObjectsArgs.builder()
                                            .bucket(source.getName())
                                            .prefix(target.toString().concat("/"))
                                            .recursive(true)
                                            .build()
                            );

                            try {
                                response.setContentType("text/html");
                                response.getWriter().println("<html><body>");

                                for (var result : minioInputStream) {
                                    var objectName = result.get().objectName();
                                    String originalName = objectName.replaceFirst("\\d+/", "");
                                    String downloadLink = toDownloadLink(source.getName(), target.toString(), originalName);
                                    response.getWriter().println("<a href='" + downloadLink + "'>" + originalName + "</a><br>");
                                }

                                response.getWriter().println("</body></html>");
                            } catch (Exception e) {
                                throw new FileOperationException(e.getMessage());
                            }
                        }
                )
        );
    }

    @Override
    @SneakyThrows
    public void removeObjects(Long target, AttachmentSource source) {
        this.getFromFuture(submit(() -> {
            try {
                var objects = minioClient.listObjects(
                        ListObjectsArgs.builder()
                                .bucket(source.getName())
                                .prefix(target.toString().concat("/"))
                                .build()
                );

                for (var result : objects) {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(source.getName())
                                    .object(result.get().objectName())
                                    .build()
                    );
                }

            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }
        }));

    }

    private String toBucketName(AttachmentSource source, Long target) {
        return source.getName().concat("-") + target;
    }

    private String toObjectNameWithFolder(FileOperationDTO dto) {
        return dto.target().toString().concat("/").concat(dto.name());
    }

    private Future<?> submit(Runnable r) {
        return applicationTaskExecutor.submit(r);
    }

    private void getFromFuture(Future<?> f) {
        try {
            f.get(TIME_LIMIT, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new FileOperationException(e);
        }
    }

    private String toDownloadLink(String source, String target, String originalName) {
        return urlPrefix+"/storage/download?source=" + source + "&name=" + originalName + "&target=" + target;
    }

}