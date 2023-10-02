package com.akerke.authservice.service;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;

public interface AuthService {

    StatusResponse validateToken(String token, TokenType type);

    TokenResponse token(User user);

}

