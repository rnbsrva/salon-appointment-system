package com.akerke.authservice.service;

import com.akerke.authservice.entity.Permission;
import com.akerke.authservice.entity.Role;
import com.akerke.authservice.repository.RoleRepository;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    void assign();

    void release();

    Set<Permission> getDefault(Role role);

    void update(Permission permissionToUpdate);
}
