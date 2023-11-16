package com.akerke.authserver.controller;

import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    ResponseEntity<?> register(
            @RequestBody RegistrationDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(dto));
    }

    @PostMapping
    ResponseEntity<?> authenticate(
            @RequestBody AuthDTO dto
    ) {
        return ResponseEntity
                .ok(authService.authenticate(dto));
    }
}
