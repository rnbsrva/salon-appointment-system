package com.akerke.authserver.domain.service;

import com.akerke.authserver.domain.dto.*;
import com.akerke.authserver.domain.model.User;

public interface AuthService {

    TokenResponseDTO authenticate(AuthDTO auth);

    User register(RegistrationDTO registration);

    TokenResponseDTO confirmEmail(OTP otp);

    TokenResponseDTO refreshToken(String refreshToken);

    void logout(String email);

    void resendEmail(String email);

    StatusResponseDTO changePassword(ChangePasswordDTO changePassword);
}
