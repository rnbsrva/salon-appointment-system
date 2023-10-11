package com.akerke.authservice.controller;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.payload.request.ForgotPasswordRequest;
import com.akerke.authservice.payload.request.ResetPasswordRequest;
import com.akerke.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.authservice.validate.BindingValidator.validateRequest;

@RestController
public class AuthController{

    @Autowired
    private AuthService authService;

    @GetMapping("validate-token")
    ResponseEntity<?> validateToken(
            @RequestParam("access_token") String token
    ) {
        return ResponseEntity
                .ok(authService.validateToken(token, TokenType.ACCESS_TOKEN));
    }

    @GetMapping("reset-password")
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
                .ok(authService.confirmForgotPassword(req,token));
    }


    @GetMapping("email-confirmation")
    ResponseEntity<?> emailConfirmation(
            @RequestParam("verification_token") String verificationToken
    ) {
        return ResponseEntity
                .ok(authService.validateToken(verificationToken, TokenType.EMAIL_VERIFICATION_TOKEN));
    }

}