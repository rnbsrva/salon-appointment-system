package com.akerke.authservice.controller;


import com.akerke.authservice.payload.request.RegistrationRequest;
import com.akerke.authservice.service.AuthService;
import com.akerke.authservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.akerke.authservice.validate.BindingValidator.validateRequest;

@RestController
public record UserController(
        UserService userService,
        AuthService authService
) {

    @PostMapping("register")
    ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest req,
            BindingResult br
    ) {
        validateRequest(br);

        var user = userService.register(req);

        return ResponseEntity
                .status(201)
                .body(this.authService.token(user));
    }

}
