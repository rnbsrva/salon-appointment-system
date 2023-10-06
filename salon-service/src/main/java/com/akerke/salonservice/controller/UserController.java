package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.UserDTO;
import com.akerke.salonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @GetMapping("{id}")
    ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll(){
        return  ResponseEntity.ok(userService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PATCH}, value = "{id}")
    ResponseEntity<?> update (
            @PathVariable Long id,
            @RequestBody UserDTO userDTO
    ){
        userService.update(userDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
