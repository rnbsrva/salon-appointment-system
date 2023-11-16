package com.akerke.authserver.domain.dto;

import java.io.Serializable;

public record RegistrationDTO(
        String name,
        String surname,
        String email,
        String password,
        String phone
) implements Serializable {
}
