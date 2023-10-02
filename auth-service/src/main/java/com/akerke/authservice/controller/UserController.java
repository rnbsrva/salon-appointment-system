package com.akerke.authservice.controller;


import com.akerke.authservice.payload.request.RegistrationRequest;
import com.akerke.authservice.service.AuthService;
import com.akerke.authservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable Long id
    ) {
        userService.delete(id);
    }

    @GetMapping("{id}")
    ResponseEntity<?> find(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(userService.find(id));
    }


}
