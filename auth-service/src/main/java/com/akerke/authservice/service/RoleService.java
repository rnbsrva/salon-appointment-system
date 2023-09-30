package com.akerke.authservice.service;

import com.akerke.authservice.entity.Role;
import com.akerke.authservice.entity.User;

import java.util.List;

public interface RoleService {
    void assign();

    void release();

    Role defaultRoles(User user);

    void update(Role roleToUpdate);
}
