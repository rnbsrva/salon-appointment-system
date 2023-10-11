package com.akerke.authservice.service;

import com.akerke.authservice.constants.TokenType;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.request.ForgotPasswordRequest;
import com.akerke.authservice.payload.request.ResetPasswordRequest;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;

public interface AuthService {

    StatusResponse validateToken(String token, TokenType type);

    TokenResponse token(User user);

    StatusResponse resetPassword(ResetPasswordRequest req);

    StatusResponse forgotPassword(String email);

    StatusResponse confirmForgotPassword(ForgotPasswordRequest req,String token);
}

