package com.akerke.authserver.domain.service.impl;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.dto.ManageRoleDTO;
import com.akerke.authserver.domain.dto.StatusResponseDTO;
import com.akerke.authserver.domain.repository.UserRepository;
import com.akerke.authserver.domain.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultRoleService implements RoleService {

    private final UserRepository userRepository;

    @Override
    public StatusResponseDTO makeChanges(
            ManageRoleDTO manageRoleDTO,
            SecurityRole role
    ) {
        var email = manageRoleDTO.email();
        var toAdd = manageRoleDTO.toAdd();

        var optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("user %s not found".formatted(email));
        }

        var user = optionalUser.get();

        if (toAdd && user.getRoles().stream().noneMatch(r -> r == role)) {
            user.getRoles().add(role);
            userRepository.save(user);
            return new StatusResponseDTO(true);
        }

        if (!toAdd) {
            user.getRoles().removeIf(r -> r == role);
            userRepository.save(user);
            return new StatusResponseDTO(true);
        }

        return new StatusResponseDTO(false);
    }
}
