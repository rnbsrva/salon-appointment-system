package com.akerke.authserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

 @GetMapping("post")
    Mono<String> greeting(
    ) {
        return Mono.just("authDTO.email() +  + authDTO.password()");
    }
}
