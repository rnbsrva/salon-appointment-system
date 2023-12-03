package com.akerke.storageservice.domain.service;

import com.akerke.storageservice.common.constants.AttachmentSource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

public interface MinIOService {
    void putObject(Long target, AttachmentSource source, MultipartFile file, Boolean isMainImage);

    void getObjectToDownload(String imageId, HttpServletResponse response);

    void removeBucket(Long target, AttachmentSource source);

    void removeObject(String id) throws ExecutionException, InterruptedException;

    void getObjects(Long target, AttachmentSource source, HttpServletResponse response);

    void removeObjects(Long target, AttachmentSource source);

    void getObject(String id, HttpServletResponse response);
}
