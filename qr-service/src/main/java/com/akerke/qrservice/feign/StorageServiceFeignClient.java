package com.akerke.qrservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "storage_service")
public interface StorageServiceFeignClient {

    @PostMapping("upload")
    void saveToStorage(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long target,
            @RequestParam(defaultValue = "QR") String source,
            @RequestParam String name
    );
}
