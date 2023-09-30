package com.akerke.authservice.service.impl;

import com.akerke.authservice.constants.SecurityRole;
import com.akerke.authservice.entity.Role;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.repository.RoleRepository;
import com.akerke.authservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void assign() {

    }

    @Override
    public void release() {

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
}
