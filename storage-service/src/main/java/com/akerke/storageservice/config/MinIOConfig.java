package com.akerke.storageservice.config;

import com.akerke.storageservice.constants.AttachmentSource;
import com.akerke.storageservice.exception.FileOperationException;
import com.akerke.storageservice.service.MinIOService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Configuration
public class MinIOConfig {

    @Value("${spring.minio.access-key}")
    private String accessKey;

    @Value("${spring.minio.secret-key}")
    private String secretKey;

    @Value("${spring.minio.endpoint}")
    private String endpoint;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public CommandLineRunner runner(
            MinioClient minio
    ) {
        return args -> {
            Arrays
                    .stream(AttachmentSource.values())
                    .forEach(attachmentSource -> {
                                try {
                                    if (minio.bucketExists(
                                            BucketExistsArgs.builder()
                                                    .bucket(attachmentSource.getName())
                                                    .build()
                                    )) {
                                        minio.makeBucket(
                                                MakeBucketArgs.builder()
                                                        .bucket(attachmentSource.getName())
                                                        .build()
                                        );
                                    }
                                } catch (Exception e) {
                                    throw new FileOperationException(e);
                                }
                            }
                    );
        };
    }

}
