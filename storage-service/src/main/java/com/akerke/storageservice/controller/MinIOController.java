package com.akerke.storageservice.controller;

import com.akerke.storageservice.common.constants.AttachmentSource;
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

    @PostMapping("upload")
    @ApiOperation("Upload an image to MinIO storage")
    void uploadImageToMinIO(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            @RequestParam Boolean isMainImage
    ) {
        minIOService.putObject(target, source, file, isMainImage);
    }

    @GetMapping("/download-by-group")
    @ApiOperation("Download files by group")
    void downloadFiles(
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            HttpServletResponse httpServletResponse
    ) {
        minIOService.getObjects(target, source, httpServletResponse);
    }

    @DeleteMapping("delete-by-group")
    @ApiOperation("Delete files by group")
    void deleteFolder(
            @RequestParam Long target,
            @RequestParam AttachmentSource source
    ) {
        minIOService.removeObjects(target, source);
    }

    @GetMapping("download-file/{imageId}")
    @ApiOperation("Download a specific file by ID")
    void downloadFile(
            @PathVariable String imageId,
            HttpServletResponse response
    ) {
        minIOService.getObjectToDownload(imageId, response);
    }

    @ApiOperation("Delete an object by ID")
    @DeleteMapping("{imageId}")
    @SneakyThrows
    void deleteObject(
            @PathVariable String imageId
    ) {
        minIOService.removeObject(imageId);
    }

    @GetMapping("/get-file/{imageId}")
    @ApiOperation("Get a specific image by ID")
    void getImage(
            HttpServletResponse response,
            @PathVariable String imageId
    ) {
        minIOService.getObject(imageId, response);
    }

}
