package com.akerke.authserver.controller;

import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.ChangePasswordDTO;
import com.akerke.authserver.domain.dto.OTP;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    ResponseEntity<?> register(
            @RequestBody RegistrationDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(dto));
    }

    @PostMapping
    ResponseEntity<?> authenticate(
            @RequestBody AuthDTO dto
    ) {
        return ResponseEntity
                .ok(authService.authenticate(dto));
    }

    @PostMapping("confirm-email")
    ResponseEntity<?> confirmEmail(
            @RequestBody OTP otp
    ) {
        return ResponseEntity
                .ok(authService.confirmEmail(otp));
    }

    @PostMapping("refresh-token")
    ResponseEntity<?> refreshToken(
            @RequestParam String refreshToken
    ) {
        return ResponseEntity.ok(
                authService.refreshToken(refreshToken)
        );
    }

    @PostMapping("logout")
    void logout(
            @RequestParam String email
    ) {
        authService.logout(email);
        SecurityContextHolder.clearContext();
    }

    @GetMapping("resend-email")
    void resendEmail(
            @RequestParam String email
    ) {
        authService.resendEmail(email);
    }

    @PostMapping("change-password")
    ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordDTO changePassword
    ) {
        return ResponseEntity
                .ok(authService.changePassword(changePassword));
    }


}
