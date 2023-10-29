package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.domain.dto.UserDTO;
import com.akerke.salonservice.domain.entity.User;
import com.akerke.salonservice.domain.repository.UserRepository;
import com.akerke.salonservice.domain.service.UserService;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.UserMapper;
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
