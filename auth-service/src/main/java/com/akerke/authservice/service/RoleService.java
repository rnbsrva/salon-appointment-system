package com.akerke.authservice.service;

import com.akerke.authservice.constants.Scope;
import com.akerke.authservice.entity.Role;
import com.akerke.authservice.entity.User;

import java.util.List;

public interface RoleService {
    void assign(Long id, Scope scope);

    void release(Long id, Scope scope);

    Role defaultRoles(User user);

    void update(Role roleToUpdate);
}
