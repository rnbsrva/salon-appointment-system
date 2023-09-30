package com.akerke.authservice.service.impl;

import com.akerke.authservice.entity.Permission;
import com.akerke.authservice.repository.PermissionRepository;
import com.akerke.authservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public void assign() {

    }

    @Override
    public void release() {

    }

    @Override
    public List<Permission> getDefault() {
        return null;
    }

    @Override
    public void update(Permission permissionToUpdate) {
        permissionRepository.save(permissionToUpdate);
    }
}
