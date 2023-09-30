package com.akerke.authservice.controller;


import com.akerke.authservice.payload.RegistrationRequest;
import com.akerke.authservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.akerke.authservice.validate.BindingValidator.validateRequest;

@RestController
@RequestMapping("user")
public record UserController(
        UserService userService
) {

    @PostMapping
    ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest req,
            BindingResult br
    ) {
        validateRequest(br);

        return ResponseEntity
                .status(201)
                .body(userService.register(req));
    }


}
