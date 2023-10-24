package com.akerke.authservice.domain.service;

import com.akerke.authservice.common.constants.SecurityRole;
import com.akerke.authservice.common.constants.TokenType;
import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.kafka.KafkaManageRoleRequest;
import com.akerke.authservice.payload.request.AuthRequest;
import com.akerke.authservice.payload.request.ForgotPasswordRequest;
import com.akerke.authservice.payload.request.ResetPasswordRequest;
import com.akerke.authservice.payload.response.StatusResponse;
import com.akerke.authservice.payload.response.TokenResponse;

public interface AuthService {

    void manageRole(KafkaManageRoleRequest kafkaManageRoleRequest, SecurityRole securityRole);

    StatusResponse validateToken(String token, TokenType type);

    TokenResponse token(User user);

    StatusResponse resetPassword(ResetPasswordRequest req);

    StatusResponse forgotPassword(String email);

    StatusResponse confirmForgotPassword(ForgotPasswordRequest req,String token);

    StatusResponse authenticate(AuthRequest authRequest);
}

