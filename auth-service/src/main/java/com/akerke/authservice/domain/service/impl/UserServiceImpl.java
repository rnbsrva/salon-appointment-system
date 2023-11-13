package com.akerke.authservice.domain.service.impl;

import com.akerke.authservice.common.constants.EmailLinkMode;
import com.akerke.authservice.common.constants.Scope;
import com.akerke.authservice.common.constants.SecurityRole;
import com.akerke.authservice.domain.dao.UserDao;
import com.akerke.authservice.domain.entity.User;
import com.akerke.exceptionlib.exception.EmailRegisteredYetException;
import com.akerke.exceptionlib.exception.EntityNotFoundException;
import com.akerke.exceptionlib.exception.PhoneNumberRegisteredYetException;
import com.akerke.authservice.infrastructure.kafka.KafkaEmailMessageDetails;
import com.akerke.authservice.infrastructure.kafka.KafkaProducer;
import com.akerke.authservice.domain.mapper.UserMapper;
import com.akerke.authservice.domain.payload.request.RegistrationRequest;
import com.akerke.authservice.domain.payload.response.StatusResponse;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.security.EmailLinkHelper;
import com.akerke.authservice.domain.service.PermissionService;
import com.akerke.authservice.domain.service.RoleService;
import com.akerke.authservice.domain.service.UserService;
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
    private final KafkaProducer kafkaProducer;
    private final EmailLinkHelper linkHelper;

    private final PasswordEncoder passwordEncoder;

    private final UserDao userDao;

    @Override
    public User register(RegistrationRequest req) {
        var mapped = userMapper.toModel(req, passwordEncoder.encode(req.password()));
        var user = userRepository.save(mapped);

        var role = roleService.defaultRoles(user);

        var permissions = permissionService.getDefault(role);
        role.setPermissions(permissions);
        roleService.update(role);

        user.setRoles(List.of(role));

        userDao.insert(user);
        return userRepository.save(user);
    }

    @Override
    public StatusResponse requestToRegistration(RegistrationRequest req) {
        var emailOptionalUser = userRepository.findByEmail(req.email());

        if (emailOptionalUser.isPresent()) {
            throw new EmailRegisteredYetException(req.email());
        }

        var phoneOptionalUser = userRepository.findByPhone(req.phone());

        if (phoneOptionalUser.isPresent()) {
            throw new PhoneNumberRegisteredYetException(req.phone());
        }

        kafkaProducer.produce(
                "email_verification",
                KafkaEmailMessageDetails.builder()
                        .subject("email verification")
                        .recipient(req.email())
                        .msgBody(linkHelper.link(EmailLinkMode.EMAIL_CONFIRMATION_LINK, req.email()))
        );

        return StatusResponse.success(req);
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
        var user = find(id);
        userRepository.deleteById(id);
        userDao.delete(user.getEmail());
    }

    @Override
    public User find(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

}
