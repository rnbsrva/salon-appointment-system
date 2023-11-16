package com.akerke.authserver.controller;

import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.model.User;
import com.akerke.authserver.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("api/v1/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService authService;
//
//    @PostMapping("register")
//    Mono<User> register(
//            @RequestBody RegistrationDTO registrationDTO
//    ) {
//        return authService.register(registrationDTO);
//    }
//
//    @PostMapping
//    Mono<?> authenticate(
//            @RequestBody AuthDTO authDTO
//            ){
//        return authService.authenticate(authDTO);
//    }
//}
