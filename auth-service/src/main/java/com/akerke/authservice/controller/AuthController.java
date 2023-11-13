package com.akerke.authservice.controller;

import com.akerke.authservice.common.annotations.ValidatedMethod;
import com.akerke.authservice.common.constants.TokenType;
import com.akerke.authservice.domain.payload.request.AuthRequest;
import com.akerke.authservice.domain.payload.request.ForgotPasswordRequest;
import com.akerke.authservice.domain.payload.request.RegistrationRequest;
import com.akerke.authservice.domain.payload.request.ResetPasswordRequest;
import com.akerke.authservice.domain.service.AuthService;
import com.akerke.loggerlib.common.annotation.EnableLoggerLib;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.authservice.common.validate.BindingValidator.validateRequest;

@RestController
@RequiredArgsConstructor
@Api(value = "Authentication API")
@EnableLoggerLib
@CrossOrigin(origins = "*")//todo
public class AuthController {

    private final AuthService authService;

    @GetMapping("validate-token")
    @ApiOperation("Validate an access token")
    ResponseEntity<?> validateToken(
            @RequestParam("access_token") String token
    ) {
        return ResponseEntity
                .ok(authService.validateToken(token, TokenType.ACCESS_TOKEN));
    }

    @ApiOperation("Request to reset a user's password")
    @PatchMapping("reset-password")
    ResponseEntity<?> requestToResetPassword(
            @RequestBody @Valid ResetPasswordRequest req,
            BindingResult br
    ) {
        validateRequest(br);

        return ResponseEntity.ok(authService.resetPassword(req));
    }

    @GetMapping("request-forgot-password")
    @ApiOperation("Request a password reset for a user if user forgot the password")
    ResponseEntity<?> requestForgotPassword(
            @RequestParam String email
    ) {
        return ResponseEntity
                .ok(authService.forgotPassword(email));
    }

    @PostMapping("confirm-forgot-password")
    @ApiOperation("Confirm a password reset request")
    ResponseEntity<?> confirmForgotPassword(
            @RequestBody @Valid ForgotPasswordRequest req,
            @RequestParam("verification_token") String token
    ) {
        return ResponseEntity
                .ok(authService.confirmForgotPassword(req, token));
    }


    @GetMapping("email-confirmation")
    @ApiOperation("Confirm an email address")
    ResponseEntity<?> emailConfirmation(
            @RequestBody @Valid RegistrationRequest request,
            @RequestParam("verification_token") String verificationToken
    ) {
        return ResponseEntity
                .ok(authService.validateToken(verificationToken, TokenType.EMAIL_VERIFICATION_TOKEN));
    }

    @PostMapping("authenticate")
    @ApiOperation("Authenticate a user")
    @ValidatedMethod
    ResponseEntity<?> authenticate(
            @RequestBody @Valid AuthRequest authRequest,
            BindingResult br
    ) {
        return ResponseEntity
                .ok(authService.authenticate(authRequest));
    }
}