package com.akerke.authservice.controller;

import com.akerke.authservice.common.constants.TokenType;
import com.akerke.authservice.domain.payload.request.AuthRequest;
import com.akerke.authservice.domain.payload.request.ForgotPasswordRequest;
import com.akerke.authservice.domain.payload.request.ResetPasswordRequest;
import com.akerke.authservice.domain.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.authservice.common.validate.BindingValidator.validateRequest;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("validate-token")
    ResponseEntity<?> validateToken(
            @RequestParam("access_token") String token
    ) {
        return ResponseEntity
                .ok(authService.validateToken(token, TokenType.ACCESS_TOKEN));
    }

    @PatchMapping("reset-password")
    ResponseEntity<?> requestToResetPassword(
            @RequestBody @Valid ResetPasswordRequest req,
            BindingResult br
    ) {
        validateRequest(br);

        return ResponseEntity.ok(authService.resetPassword(req));
    }

    @GetMapping("request-forgot-password")
    ResponseEntity<?> requestForgotPassword(
            @RequestParam String email
    ) {
        return ResponseEntity
                .ok(authService.forgotPassword(email));
    }

    @PostMapping("confirm-forgot-password")
    ResponseEntity<?> confirmForgotPassword(
            @RequestBody @Valid ForgotPasswordRequest req,
            @RequestParam("verification_token") String token
    ) {
        return ResponseEntity
                .ok(authService.confirmForgotPassword(req, token));
    }


    @GetMapping("email-confirmation")
    ResponseEntity<?> emailConfirmation(
            @RequestParam("verification_token") String verificationToken
    ) {
        return ResponseEntity
                .ok(authService.validateToken(verificationToken, TokenType.EMAIL_VERIFICATION_TOKEN));
    }

    @PostMapping("authenticate")
    ResponseEntity<?> authenticate(
            @RequestBody @Valid AuthRequest authRequest
    ) {
        return ResponseEntity
                .ok(authService.authenticate(authRequest));
    }
}