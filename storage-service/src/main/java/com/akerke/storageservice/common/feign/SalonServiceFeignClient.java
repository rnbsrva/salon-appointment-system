package com.akerke.storageservice.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "salon-service")
public interface SalonServiceFeignClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/salon-service/salon/add-image/{id}")
    ResponseEntity<?> addImageToSalon(
            @RequestParam String imageId,
            @PathVariable Long id,
            @RequestParam Boolean isMainImage
    );

    @RequestMapping(method = RequestMethod.PUT, value = "/salon-service/master/add-image/{id}")
    ResponseEntity<?> addImageToMaster(
            @RequestParam String imageId,
            @PathVariable Long id,
            @RequestParam Boolean isMainImage
    );

    @DeleteMapping("/salon-service/salon/delete-image/{imageId}")
    ResponseEntity<?> deleteImageOfSalon(
            @PathVariable String imageId
    );

    @DeleteMapping("/salon-service/master/delete-image/{imageId}")
    ResponseEntity<?> deleteImageOfMaster(
            @PathVariable String imageId
    );

}