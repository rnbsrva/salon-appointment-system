package com.akerke.authserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
public class RoleController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    String admin(){
        return "ADMIN";
    }
}
