package com.akerke.authservice.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "salon_service_client"
)
public interface SalonServiceClient {
}
