package com.akerke.authserver.controller;

import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.validationlib.annotation.Validate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    @PostMapping("post")
    Mono<String> greeting(
            @RequestBody @Validate AuthDTO authDTO
    ){
        return Mono.just(authDTO.email() + " " + authDTO.password());
    }
}
