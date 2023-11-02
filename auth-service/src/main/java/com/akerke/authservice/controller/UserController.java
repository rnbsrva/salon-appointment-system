package com.akerke.authservice.controller;


import com.akerke.authservice.domain.payload.request.RegistrationRequest;
import com.akerke.authservice.domain.service.AuthService;
import com.akerke.authservice.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.authservice.common.validate.BindingValidator.validateRequest;

@RestController
@RequiredArgsConstructor
@ResponseBody
@Api("User API")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("register")
    @ApiOperation("Register a new user")
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
    @ApiOperation("Delete a user by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable Long id
    ) {
        userService.delete(id);
    }

    @GetMapping("{id}")
    @ApiOperation("Retrieve a user by ID")
    ResponseEntity<?> find(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(userService.find(id));
    }


}
