package com.akerke.authservice.service.impl;

import com.akerke.authservice.security.JwtUtil;
import com.akerke.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwt;

    @Override
    public Boolean validateAccessToken(String token) {
        return null;
    }

}
