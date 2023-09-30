package com.akerke.authservice.service;

import com.akerke.authservice.entity.Permission;

import java.util.List;

public interface PermissionService {
    void assign();

    void release();

    List<Permission> getDefault();

    void update(Permission permissionToUpdate);
}
