package com.akerke.qrservice.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "storage-service")
public interface StorageServiceFeignClient {

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Headers("Content-Type: multipart/form-data")
    void saveToStorage(
            @RequestPart("file") MultipartFile file,
            @RequestParam Long target,
            @RequestParam String source,
            @RequestParam String name
    );

    default void saveToStorage(
           MultipartFile file,
           Long target,
           String name
    ) {
        this.saveToStorage(file, target, "QR_IMAGE", name);
    }

}
