package com.akerke.storageservice.domain.service;

import com.akerke.storageservice.common.constants.AttachmentSource;
import com.akerke.storageservice.domain.dto.FileOperationDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

public interface MinIOService {
    void putObject(FileOperationDTO dto, MultipartFile file);

    void putQR(FileOperationDTO dto, MultipartFile file);

    void getQR(FileOperationDTO dto, HttpServletResponse response);

    void getObject(FileOperationDTO dto, HttpServletResponse response);

    void removeBucket(Long target, AttachmentSource source);

    void removeObject(FileOperationDTO dto) throws ExecutionException, InterruptedException;

    void getObjects(Long target, AttachmentSource source, HttpServletResponse response);

    void removeObjects(Long target, AttachmentSource source);

}