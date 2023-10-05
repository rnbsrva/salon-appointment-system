package com.akerke.authservice.service;

import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.request.RegistrationRequest;

public interface UserService {

    User register(RegistrationRequest req);

    User findByEmail(String email);

    void verifyEmail(User user);

    void delete(Long id);

    User find(Long id);

    void update(User user);
}
