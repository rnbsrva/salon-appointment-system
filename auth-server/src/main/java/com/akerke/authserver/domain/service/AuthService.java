package com.akerke.authserver.domain.service;

import com.akerke.authserver.domain.dto.AuthDTO;
import com.akerke.authserver.domain.dto.TokenResponseDTO;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<TokenResponseDTO> authenticate(AuthDTO auth);

}
