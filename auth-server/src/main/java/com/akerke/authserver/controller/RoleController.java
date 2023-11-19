package com.akerke.authserver.controller;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.dto.ManageRoleDTO;
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
    @PostMapping("manage-app-manager")
    ResponseEntity<?> manageApplicationManager(
            @RequestBody ManageRoleDTO manageRoleDTO
    ) {
        return ResponseEntity.ok(roleService.makeChanges(manageRoleDTO, SecurityRole.APPLICATION_MANAGER));
    }

    @PreAuthorize("hasAnyRole('APPLICATION_ADMIN', 'APPLICATION_MANAGER')")
    @PostMapping("manage-admin")
    ResponseEntity<?> manageAdmin(
            @RequestBody ManageRoleDTO manageRoleDTO
    ) {
        return ResponseEntity.ok(roleService.makeChanges(manageRoleDTO, SecurityRole.ADMIN));
    }

    @PreAuthorize("hasAnyRole('APPLICATION_ADMIN', 'APPLICATION_MANAGER', 'ADMIN')")
    @PostMapping("manage-manager")
    ResponseEntity<?> manageManager(
            @RequestBody ManageRoleDTO manageRoleDTO
    ) {
        return ResponseEntity.ok(roleService.makeChanges(manageRoleDTO, SecurityRole.MANAGER));
    }


}
