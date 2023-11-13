package com.akerke.authservice.security.impl;

import com.akerke.exceptionlib.exception.InvalidCredentialsException;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.security.DefaultUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation for loading user details by username (email).
 */
@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads the user details by the specified email.
     *
     * @param email the email of the user
     * @return the UserDetails object
     * @throws InvalidCredentialsException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(
            String email
    ) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(DefaultUserDetails::new)
                .orElseThrow(InvalidCredentialsException::new);

    }

}
