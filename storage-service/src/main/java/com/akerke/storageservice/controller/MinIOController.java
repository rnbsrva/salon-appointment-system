package com.akerke.storageservice.controller;

import com.akerke.storageservice.constants.AttachmentSource;
import com.akerke.storageservice.dto.FileOperationDTO;
import com.akerke.storageservice.service.MinIOService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("storage")
public class MinIOController {

    private final MinIOService minIOService;

    @PostMapping("/upload")
    void uploadFileToMinIO(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            @RequestParam String name
    ) {
        minIOService.putObject(new FileOperationDTO(target, source, name), file);
    }

    @GetMapping("/downloadByGroup")
    void downloadFiles(
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            HttpServletResponse httpServletResponse
    ){
        minIOService.getObjects(target, source, httpServletResponse);
    }

    @DeleteMapping("/deleteByGroup")
    void deleteFolder(
            @RequestParam Long target,
            @RequestParam AttachmentSource source
    ){
        minIOService.removeObjects(target, source);

    }

    @GetMapping("/download")
    void downloadFile(
            @RequestParam String name,
            @RequestParam Long target,
            @RequestParam AttachmentSource source,
            HttpServletResponse response
    ) {
        minIOService.getObject(new FileOperationDTO(target, source, name), response);
    }

    @PatchMapping("delete")
    @SneakyThrows
    void deleteObject(
            @RequestBody FileOperationDTO dto
    ) {
        minIOService.removeObject(dto);
    }
}
