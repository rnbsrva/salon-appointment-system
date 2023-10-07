package com.akerke.authservice.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InvalidCredentialsException extends UsernameNotFoundException {
    public InvalidCredentialsException(
    ) {
        super("invalid credentials");
    }
}
