package com.akerke.authservice.service;

import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.RegistrationRequest;

public interface UserService {

    User register(RegistrationRequest req);


}
