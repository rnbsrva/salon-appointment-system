package com.akerke.authservice.security.impl;

import com.akerke.authservice.common.exception.InvalidCredentialsException;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.security.DefaultUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByEmail(email);

        return optionalUser
                .map(DefaultUserDetails::new)
                .orElseThrow(InvalidCredentialsException::new);
    }

}
