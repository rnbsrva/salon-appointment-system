package com.akerke.storageservice.controller;

import com.akerke.storageservice.common.constants.AttachmentSource;
import com.akerke.storageservice.domain.request.FileOperationRequest;
import com.akerke.storageservice.domain.service.MinIOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("storage")
@Api("Storage Minio API")
public class MinIOController {

    private final MinIOService minIOService;

    @PostMapping("/upload")
    @ApiOperation("Upload a file to MinIO storage")
    void uploadFileToMinIO(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            @RequestParam String name
    ) {
        minIOService.putObject(new FileOperationRequest(target, source, name), file);
    }

    @GetMapping("/downloadByGroup")
    @ApiOperation("Download files by group")
    void downloadFiles(
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            HttpServletResponse httpServletResponse
    ) {
        minIOService.getObjects(target, source, httpServletResponse);
    }

    @DeleteMapping("/deleteByGroup")
    @ApiOperation("Delete files by group")
    void deleteFolder(
            @RequestParam Long target,
            @RequestParam AttachmentSource source
    ) {
        minIOService.removeObjects(target, source);
    }

    @GetMapping("/download")
    @ApiOperation("Download a specific file")
    void downloadFile(
            @RequestParam String name,
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            HttpServletResponse response
    ) {
        minIOService.getObject(new FileOperationRequest(target, source, name), response);
    }


    @ApiOperation("Delete an object")
    @PatchMapping("delete")
    @SneakyThrows
    void deleteObject(
            @RequestBody FileOperationRequest dto
    ) {
        minIOService.removeObject(dto);
    }



}
