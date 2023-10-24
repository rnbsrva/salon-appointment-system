package com.akerke.authservice.domain.service;

import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.payload.request.RegistrationRequest;
import com.akerke.authservice.payload.response.StatusResponse;

public interface UserService {

    User register(RegistrationRequest req);

    StatusResponse requestToRegistration(RegistrationRequest req);

    User findByEmail(String email);

    void verifyEmail(User user);

    void delete(Long id);

    User find(Long id);

    void update(User user);
}
