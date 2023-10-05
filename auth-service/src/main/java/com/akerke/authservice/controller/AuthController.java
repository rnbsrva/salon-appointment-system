package com.akerke.authservice.controller;

import com.akerke.authservice.constants.EmailLinkMode;
import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.payload.request.ResetPasswordRequest;
import com.akerke.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.akerke.authservice.validate.BindingValidator.validateRequest;

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


    @GetMapping("reset-password")
    ResponseEntity<?> requestToResetPassword(
            @RequestBody @Valid ResetPasswordRequest req,
            BindingResult br
    ) {
        validateRequest(br);
        return ResponseEntity.ok(authService.resetPassword(req));
    }

    @GetMapping("forgot-password")
    ResponseEntity<?> forgotPassword(
            @RequestParam("verification_token") String verificationToken
    ) {
return null;// TODO: 10/5/2023
    }


    @GetMapping("email-confirmation")
    ResponseEntity<?> emailConfirmation(
            @RequestParam("verification_token") String verificationToken
    ) {
        var response = authService.validateToken(verificationToken, TokenType.VERIFICATION_TOKEN);
        return ResponseEntity
                .ok(response);
    }


}