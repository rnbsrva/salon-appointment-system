package com.akerke.authservice.service.impl;

import com.akerke.authservice.entity.User;
import com.akerke.authservice.exception.EmailRegisteredYetException;
import com.akerke.authservice.mapper.UserMapper;
import com.akerke.authservice.payload.RegistrationRequest;
import com.akerke.authservice.repository.UserRepository;
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

    @Override
    public User register(RegistrationRequest req) {
        var optionalUser = userRepository.findByEmail(req.email());

        if (optionalUser.isPresent()) {
            throw new EmailRegisteredYetException(req.email());
        }

        var user = userMapper.toModel(req, List.of(roleService.defaultRole()));

        return userRepository.save(user);
    }
}
