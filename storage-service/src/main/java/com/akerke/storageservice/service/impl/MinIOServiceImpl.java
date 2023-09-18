package com.akerke.storageservice.service.impl;

import com.akerke.storageservice.constants.AttachmentSource;
import com.akerke.storageservice.dto.FileOperationDTO;
import com.akerke.storageservice.service.MinIOService;
import io.minio.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {

    private final MinioClient minioClient;

    @Override
    public void putObject(FileOperationDTO dto, MultipartFile file) {
        var bucketName = toBucketName(dto.source(),dto.target());
//        checkBucketExisting(bucketName);
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
    }

    @Override
    @SneakyThrows
    public void getObject(FileOperationDTO dto, HttpServletResponse response) {

        var minioInputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(dto.source().getName())
                        .object(dto.target().toString().concat("/").concat(dto.name())) // extract to method
                        .build()
        );

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
        }

    }

    @Override
    public void removeBucket(Long target, AttachmentSource source) {
        var bucketName = toBucketName(source,target);

        try {
            minioClient.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeObject(FileOperationDTO dto) {
        var bucketName = toBucketName(dto.source(),dto.target());
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(dto.source().getName())
                            .object(dto.target().toString().concat("/").concat(dto.name()))
                            .build()
            );
        } catch (Exception e){
            throw new RuntimeException(e);
        }

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
}
