package com.akerke.storageservice.config;

import com.akerke.storageservice.common.constants.AttachmentSource;
import com.akerke.storageservice.common.exception.BucketInitializerException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Slf4j
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
    public CommandLineRunner bucketListInitializer(
            MinioClient minio
    ) {
        return args -> {
            Arrays
                    .stream(AttachmentSource.values())
                    .filter(
                            attachmentSource -> {
                                try {
                                    return !minio.bucketExists(
                                            BucketExistsArgs.builder()
                                                    .bucket(attachmentSource.getName())
                                                    .build()
                                    );
                                } catch (Exception e) {
                                    throw new BucketInitializerException(e.getMessage());
                                }
                            }
                    )
                    .forEach(attachmentSource -> {
                                try {
                                    minio.makeBucket(
                                            MakeBucketArgs.builder()
                                                    .bucket(attachmentSource.getName())
                                                    .build()
                                    );
                                } catch (Exception e) {
                                    throw new BucketInitializerException(e.getMessage());
                                }
                            }
                    );
        };
    }

}
