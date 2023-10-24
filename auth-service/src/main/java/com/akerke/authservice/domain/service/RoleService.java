package com.akerke.authservice.domain.service;

import com.akerke.authservice.common.constants.Scope;
import com.akerke.authservice.domain.entity.Role;
import com.akerke.authservice.domain.entity.User;

public interface RoleService {
    void assign(Long id, Scope scope);

    void release(Long id, Scope scope);

    Role defaultRoles(User user);

    void update(Role roleToUpdate);

    void delete(Long id);
}
