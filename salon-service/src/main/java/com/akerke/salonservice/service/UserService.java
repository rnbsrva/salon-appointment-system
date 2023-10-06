package com.akerke.salonservice.service;

import com.akerke.salonservice.dto.UserDTO;
import com.akerke.salonservice.entity.User;

import java.util.List;

public interface UserService {

    User save (UserDTO userDTO);

    User getById (Long id);

    List<User> getAll();

    void delete (Long id);

    void update(UserDTO userDTO, Long id);

}
