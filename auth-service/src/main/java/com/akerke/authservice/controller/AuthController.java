package com.akerke.authservice.controller;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record AuthController(
        AuthService authService
) {

    @GetMapping("validate-token")
    ResponseEntity<?> validateToken(
            @RequestParam("token_type") TokenType type,
            @RequestParam String token
    ) {
        return ResponseEntity
                .ok(authService.validateToken(token, type));
    }



}