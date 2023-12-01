package com.akerke.storageservice.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "salon-service")
public interface SalonServiceFeignClient {

    @RequestMapping(method = RequestMethod.PUT, value = "salon/add-image/{id}")
    ResponseEntity<?> addImageToSalon(
            @RequestParam String imageId,
            @PathVariable Long id,
            @RequestParam Boolean isMainImage
    );

    @RequestMapping(method = RequestMethod.PUT, value = "master/add-image/{id}")
    ResponseEntity<?> addImageToMaster(
            @RequestParam String imageId,
            @PathVariable Long id,
            @RequestParam Boolean isMainImage
    );


}