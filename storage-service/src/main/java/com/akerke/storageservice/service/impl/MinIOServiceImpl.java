package com.akerke.storageservice.service.impl;

import com.akerke.storageservice.constants.AttachmentSource;
import com.akerke.storageservice.dto.FileOperationDTO;
import com.akerke.storageservice.service.MinIOService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {

    private final MinioClient minioClient;
    private final AsyncTaskExecutor applicationTaskExecutor;

    @Override
    @SneakyThrows
    public void putObject(FileOperationDTO dto, MultipartFile file) {
        applicationTaskExecutor.submit(()-> {
            try {
                var in = new ByteArrayInputStream(file.getBytes());
                var objectName = file.getOriginalFilename();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(dto.source().getName())
                                .object(dto.target().toString().concat("/").concat(objectName))
                                .stream(in, -1, 10485760).build());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).get(1, TimeUnit.MINUTES);
    }

    @Override
    @SneakyThrows
    public void getObject(FileOperationDTO dto, HttpServletResponse response) {
        applicationTaskExecutor.submit(()-> {
            GetObjectResponse minioInputStream;
            try {
                minioInputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(dto.source().getName())
                                .object(toObjectNameWithFolder(dto))
                                .build()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        }).get(1, TimeUnit.MINUTES);
    }

    @Override
    @SneakyThrows
    public void removeBucket(Long target, AttachmentSource source) {

        applicationTaskExecutor.submit(()-> {

        var bucketName = toBucketName(source,target);

        try {
            minioClient.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e){
            throw new RuntimeException(e);
        }}).get(1, TimeUnit.MINUTES);

    }

    @Override
    @SneakyThrows
    public void removeObject(FileOperationDTO dto) {
        applicationTaskExecutor.submit(()-> {

            try {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(dto.source().getName())
                                .object(toObjectNameWithFolder(dto))
                                .build()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).get(1, TimeUnit.MINUTES);
    }

    @Override
    @SneakyThrows
    public void getObjects(Long target, AttachmentSource source, HttpServletResponse response) {
        applicationTaskExecutor.submit(()-> {

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

                for (Result<Item> result : minioInputStream) {
                    var objectName = result.get().objectName();
                    String originalName = objectName.replaceFirst("\\d+/", "");
                    String downloadLink = "http://localhost:8080/storage/download?source=" + source + "&name=" + originalName + "&target=" + target;
                    response.getWriter().println("<a href='" + downloadLink + "'>" + originalName + "</a><br>");
                }
                response.getWriter().println("</body></html>");
            } catch (Exception e){
                throw new RuntimeException(e);
            }

        }).get(1, TimeUnit.MINUTES);
    }

    @Override
    @SneakyThrows
    public void removeObjects(Long target, AttachmentSource source) {
        applicationTaskExecutor.submit(()->{
            try {
                var objects = minioClient.listObjects(
                        ListObjectsArgs.builder()
                                .bucket(source.getName())
                                .prefix(target.toString().concat("/"))
                                .build()
                );

                for (Result<Item> result : objects) {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(source.getName())
                                    .object(result.get().objectName())
                                    .build()
                    );
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).get(1, TimeUnit.MINUTES);
    }

    @SneakyThrows
    private void checkBucketExisting(String bucketName) {
        var bucketExist = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );
        System.out.println(bucketExist);
        if (!bucketExist) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        }
    }

    private String toBucketName(AttachmentSource source,Long target) {
        return source.getName().concat("-") + target;
    }

    private String toObjectNameWithFolder (FileOperationDTO dto) {
        return dto.target().toString().concat("/").concat(dto.name());
    }
}
