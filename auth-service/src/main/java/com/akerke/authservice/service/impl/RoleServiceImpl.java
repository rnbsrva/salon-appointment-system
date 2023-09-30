package com.akerke.authservice.service.impl;

import com.akerke.authservice.entity.Role;
import com.akerke.authservice.repository.RoleRepository;
import com.akerke.authservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Role defaultRole() {
        return null;
    }

    @Override
    public void update(Role roleToUpdate) {
        roleRepository.save(roleToUpdate);
    }
}
