package com.akerke.authserver.controller;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('APPLICATION_ADMIN')")
    @PostMapping("add-app-manager")
    ResponseEntity<?> manageApplicationManager(
            @RequestParam String email, Boolean toAdd
    ) {
        return ResponseEntity.ok(roleService.makeChanges(email, toAdd, SecurityRole.APPLICATION_MANAGER));
    }

    @PreAuthorize("hasAnyRole('APPLICATION_ADMIN', 'APPLICATION_MANAGER')")
    @PostMapping("manage-app-manager")
    ResponseEntity<?> manageAdmin(
            @RequestParam String email, Boolean toAdd
    ) {
        return ResponseEntity.ok(roleService.makeChanges(email, toAdd, SecurityRole.ADMIN));
    }

    @PreAuthorize("hasAnyRole('APPLICATION_ADMIN', 'APPLICATION_MANAGER', 'ADMIN')")
    @PostMapping("manage-app-manager")
    ResponseEntity<?> manageManager(
            @RequestParam String email, Boolean toAdd
    ) {
        return ResponseEntity.ok(roleService.makeChanges(email, toAdd, SecurityRole.MANAGER));
    }


}
