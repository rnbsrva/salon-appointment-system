package com.akerke.authservice.domain.service;

import com.akerke.authservice.common.constants.Scope;
import com.akerke.authservice.domain.entity.Permission;
import com.akerke.authservice.domain.entity.Role;

import java.util.Set;

public interface PermissionService {

    Permission initialize(Role role, Scope scope);

    Set<Permission> getDefault(Role role);

    void update(Permission permissionToUpdate);

    void delete(Long id);
}
