package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.UserDTO;
import com.akerke.salonservice.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.common.validate.Validator.validate;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Api("User API")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @ApiOperation("Create a new user")
    ResponseEntity<?> save (
            @Valid
            @RequestBody UserDTO userDTO,
            BindingResult bindingResult
            ) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @GetMapping("{id}")
    @ApiOperation("Get an user by ID")
    ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping()
    @ApiOperation("Get a list of all users")
    ResponseEntity<?> getAll(){
        return  ResponseEntity.ok(userService.getAll());
    }

    @ApiOperation("Update an user by ID")
    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PATCH}, value = "{id}")
    ResponseEntity<?> update (
            @PathVariable Long id,
            @RequestBody UserDTO userDTO
    ){
        userService.update(userDTO, id);
        return ResponseEntity.accepted().build();
    }

    @ApiOperation("Delete an user by ID")
    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
