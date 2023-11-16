package com.akerke.authserver.domain.service;

import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.OTP;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.dto.TokenResponseDTO;
import com.akerke.authserver.domain.model.User;

public interface AuthService {

    TokenResponseDTO authenticate(AuthDTO auth);

    User register(RegistrationDTO registration);

    TokenResponseDTO confirmEmail(OTP otp);

    TokenResponseDTO refreshToken(String refreshToken);
}
