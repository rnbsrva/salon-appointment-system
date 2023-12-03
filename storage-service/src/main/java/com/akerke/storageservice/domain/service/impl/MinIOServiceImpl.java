package com.akerke.storageservice.domain.service.impl;

import com.akerke.storageservice.common.constants.AttachmentSource;
import com.akerke.storageservice.common.exception.FileOperationException;
import com.akerke.storageservice.common.exception.ImageMetadataNotFoundException;
import com.akerke.storageservice.common.feign.SalonServiceFeignClient;
import com.akerke.storageservice.domain.entity.ImageMetadata;
import com.akerke.storageservice.domain.repository.ImageMetadataRepository;
import com.akerke.storageservice.domain.service.MinIOService;
import io.minio.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
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
    private final ImageMetadataRepository repository;
    private final SalonServiceFeignClient feignClient;

    private static final Integer TIME_LIMIT = 3;

    @Value("${spring.minio.download-link-prefix}")
    private String urlPrefix;

    @Override
    public void putObject(Long target, AttachmentSource source, MultipartFile file, Boolean isMainImage) {

        this.getFromFuture(submit(() -> {
            try {
                if (repository.findByAttachmentSourceAndTargetAndIsMainImage(source, target, isMainImage).isPresent()){
                    var image = repository.findByAttachmentSourceAndTargetAndIsMainImage(source, target, isMainImage).get();
                    image.setIsMainImage(false);
                    repository.save(image);
                }

                var imageMetadata = repository.save(toImageMetadata(isMainImage, target, source, file));

                if (source == AttachmentSource.SALON_IMAGE)
                    this.feignClient.addImageToSalon(imageMetadata.getId(), imageMetadata.getTarget(), isMainImage);
                else
                    this.feignClient.addImageToMaster(imageMetadata.getId(), imageMetadata.getTarget(), isMainImage);

                var in = new ByteArrayInputStream(file.getBytes());
                var objectName = file.getOriginalFilename();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(source.getName())
                                .object(target.toString().concat("/").concat(objectName))
                                .stream(in, -1, 10485760).build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }
        }));
    }


    @Override
    @SneakyThrows
    public void getObjectToDownload(String imageId, HttpServletResponse response) {
        var image = this.getById(imageId);

        this.getFromFuture(submit(() -> {
            GetObjectResponse minioInputStream;
            try {
                minioInputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(image.getAttachmentSource().getName())
                                .object(toObjectNameWithFolder(image))
                                .build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + image.getName() + "\"");

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
    public void removeObject(String imageId) {
        this.getFromFuture(submit(() -> {

            var image = this.getById(imageId);

            if(image.getAttachmentSource()==AttachmentSource.SALON_IMAGE)
                this.feignClient.deleteImageOfSalon(imageId);
            else
                this.feignClient.deleteImageOfMaster(imageId);

            try {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(image.getAttachmentSource().getName())
                                .object(toObjectNameWithFolder(image))
                                .build()
                );
                repository.delete(image);
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

    @Override
    public void getObject(String id, HttpServletResponse response) {

        this.getFromFuture(submit(() -> {

            var imageMetadata = this.getById(id);

            GetObjectResponse minioInputStream;
            try {
                minioInputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(imageMetadata.getAttachmentSource().getName())
                                .object(toObjectNameWithFolder(imageMetadata))
                                .build()
                );
            } catch (Exception e) {
                throw new FileOperationException(e.getMessage());
            }
            response.setHeader("Content-Disposition", "inline; filename=\"" + imageMetadata.getName() + "\"");

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

    private String toBucketName(AttachmentSource source, Long target) {
        return source.getName().concat("-") + target;
    }


    private String toObjectNameWithFolder(ImageMetadata image) {
        return image.getTarget().toString().concat("/").concat(image.getName());
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
        return urlPrefix + "/storage/download?source=" + source + "&name=" + originalName + "&target=" + target;
    }

    private static ImageMetadata toImageMetadata(Boolean isMainImage, Long target, AttachmentSource source, MultipartFile file) {
        return new ImageMetadata(
                isMainImage,
                file.getOriginalFilename(),
                target,
                source
        );
    }

    private ImageMetadata getById(String id){
        return repository.findById(id).orElseThrow(
                () -> new ImageMetadataNotFoundException("Image with id %s Not Found".formatted(id))
        );
    }

}
