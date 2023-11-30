package com.akerke.storageservice.domain.service;

import com.akerke.storageservice.common.constants.AttachmentSource;
import com.akerke.storageservice.domain.request.FileOperationRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

public interface MinIOService {
    void putObject(FileOperationRequest dto, MultipartFile file);

    void getObjectToDownload(FileOperationRequest dto, HttpServletResponse response);

    void getObject(FileOperationRequest dto, HttpServletResponse response);

    void removeBucket(Long target, AttachmentSource source);

    void removeObject(FileOperationRequest dto) throws ExecutionException, InterruptedException;

    void getObjects(Long target, AttachmentSource source, HttpServletResponse response);

    void removeObjects(Long target, AttachmentSource source);

}
