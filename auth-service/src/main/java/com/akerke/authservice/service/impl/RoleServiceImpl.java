package com.akerke.authservice.service.impl;

import com.akerke.authservice.constants.Scope;
import com.akerke.authservice.constants.SecurityRole;
import com.akerke.authservice.entity.Role;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.exception.EntityNotFoundException;
import com.akerke.authservice.repository.RoleRepository;
import com.akerke.authservice.service.PermissionService;
import com.akerke.authservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionService permissionService;


    @Override
    public void assign(Long id, Scope scope) {
        var role = find(id);
        var permissionToAssign = permissionService.initialize(role, scope);

        role.getPermissions().add(permissionToAssign);
        roleRepository.save(role);
    }

    @Override
    public void release(Long id, Scope scope) {
        var role = find(id);
        var optionalPermission = role.getPermissions().stream().filter(p -> p.getType() == scope).findFirst();

        if (optionalPermission.isEmpty()) {
            return;
        }
        var permission = optionalPermission.get();

        permissionService.delete(permission.getId());
        role.getPermissions().remove(permission);

        roleRepository.save(role);
    }

    private Role find(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Role.class));
    }

    @Override
    public Role defaultRoles(User user) {
        return roleRepository.save(
                new Role(SecurityRole.USER, user)
        );
    }


    @Override
    public void update(Role roleToUpdate) {
        roleRepository.save(roleToUpdate);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
