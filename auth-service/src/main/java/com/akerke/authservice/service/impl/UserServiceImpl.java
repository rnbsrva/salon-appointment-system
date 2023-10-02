package com.akerke.authservice.service.impl;

import com.akerke.authservice.constants.Scope;
import com.akerke.authservice.constants.SecurityRole;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.exception.EmailRegisteredYetException;
import com.akerke.authservice.exception.EntityNotFoundException;
import com.akerke.authservice.mapper.UserMapper;
import com.akerke.authservice.payload.request.RegistrationRequest;
import com.akerke.authservice.repository.UserRepository;
import com.akerke.authservice.service.PermissionService;
import com.akerke.authservice.service.RoleService;
import com.akerke.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegistrationRequest req) {
        var optionalUser = userRepository.findByEmail(req.email());

        if (optionalUser.isPresent()) {
            throw new EmailRegisteredYetException(req.email());
        }

        var user = userRepository.save(userMapper.toModel(req, passwordEncoder.encode(req.password())));
        var role = roleService.defaultRoles(user);

        var permissions = permissionService.getDefault(role);
        role.setPermissions(permissions);
        roleService.update(role);

        user.setRoles(List.of(role));

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("user with email %s not found".formatted(email)));
    }

    @Override
    public void verifyEmail(User user) {
        user.setEmailVerified(true);

        var role = user.getRoles().stream()
                .filter(r -> r.getRole() == SecurityRole.USER)
                .findFirst().orElseThrow();

        roleService.assign(role.getId(), Scope.EMAIL);

        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User find(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));
    }
}
