package com.akerke.authserver.domain.service;

import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.OTP;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.dto.TokenResponseDTO;
import com.akerke.authserver.domain.model.User;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<TokenResponseDTO> authenticate(AuthDTO auth);

    Mono<User> register(RegistrationDTO registration);

    Mono<TokenResponseDTO> confirmEmail(OTP otp);
}
