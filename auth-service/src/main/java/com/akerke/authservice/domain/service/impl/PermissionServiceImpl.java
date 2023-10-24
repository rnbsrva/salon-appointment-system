package com.akerke.authservice.domain.service.impl;

import com.akerke.authservice.common.constants.Scope;
import com.akerke.authservice.domain.entity.Permission;
import com.akerke.authservice.domain.entity.Role;
import com.akerke.authservice.domain.repository.PermissionRepository;
import com.akerke.authservice.domain.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;


    @Override
    public Permission initialize(Role role, Scope scope) {
        return permissionRepository.save(new Permission(scope, role));
    }

    @Override
    public Set<Permission> getDefault(Role role) {
        var scopeSet = Scope.defaultSet();

        return scopeSet.stream()
                .map(scope -> new Permission(scope, role))
                .map(permissionRepository::save)
                .collect(Collectors.toSet());
    }

    @Override
    public void update(Permission permissionToUpdate) {
        permissionRepository.save(permissionToUpdate);
    }

    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}
