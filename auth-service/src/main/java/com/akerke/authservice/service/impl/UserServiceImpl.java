package com.akerke.authservice.service.impl;

import com.akerke.authservice.entity.User;
import com.akerke.authservice.exception.EmailRegisteredYetException;
import com.akerke.authservice.mapper.UserMapper;
import com.akerke.authservice.payload.request.RegistrationRequest;
import com.akerke.authservice.repository.UserRepository;
import com.akerke.authservice.service.PermissionService;
import com.akerke.authservice.service.RoleService;
import com.akerke.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Override
    public User register(RegistrationRequest req) {
        var optionalUser = userRepository.findByEmail(req.email());

        if (optionalUser.isPresent()) {
            throw new EmailRegisteredYetException(req.email());
        }

        var user = userRepository.save(userMapper.toModel(req));
        var role = roleService.defaultRoles(user);

        var permissions = permissionService.getDefault(role);
        role.setPermissions(permissions);
        roleService.update(role);

        user.setRoles(List.of(role));

        return userRepository.save(user);
    }
}
