package com.akerke.authserver.domain.service;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.dto.StatusResponseDTO;
import com.akerke.authserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultRoleService implements RoleService {

    private final UserRepository userRepository;

    @Override
    public StatusResponseDTO makeChanges(
            String email,
            Boolean toAdd,
            SecurityRole role
    ) {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with %s not found".formatted(email)));

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
