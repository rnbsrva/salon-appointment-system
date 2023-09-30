package com.akerke.authservice.service;

import com.akerke.authservice.entity.Role;

public interface RoleService {
    void assign();

    void release();

    Role defaultRole();

    void update(Role roleToUpdate);
}
