package com.akerke.authserver.domain.service.impl;

import com.akerke.authserver.common.exception.InvalidCredentialsException;
import com.akerke.authserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String email
    ) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);
    }
}
