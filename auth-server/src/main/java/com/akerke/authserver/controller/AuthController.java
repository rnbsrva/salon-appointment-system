package com.akerke.authserver.controller;

import com.akerke.authserver.common.jwt.JwtRouteValidator;
import com.akerke.authserver.domain.dto.*;
import com.akerke.authserver.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtRouteValidator jwtRouteValidator;

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

    @PostMapping("validate-route")
    ResponseEntity<?> validateRoute(
            @RequestBody RouteValidateDTO routeValidateDTO
    ) {
        return ResponseEntity.ok(jwtRouteValidator.canActivate(routeValidateDTO));
    }


}
