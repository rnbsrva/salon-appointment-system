package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.dto.UserDTO;
import com.akerke.salonservice.entity.User;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.mapper.UserMapper;
import com.akerke.salonservice.repository.UserRepository;
import com.akerke.salonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(UserDTO userDTO) {
        return userRepository.save(userMapper.toModel(userDTO));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(User.class, id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(this.getById(id));
    }

    @Override
    public void update(UserDTO userDTO, Long id) {
        var user = this.getById(id);
        userMapper.update(userDTO, user);
        userRepository.save(user);
    }


}
