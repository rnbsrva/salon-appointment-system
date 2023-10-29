package com.akerke.salonservice.service;

import com.akerke.salonservice.common.constants.Gender;
import com.akerke.salonservice.domain.dto.UserDTO;
import com.akerke.salonservice.domain.entity.User;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.mapper.UserMapper;
import com.akerke.salonservice.domain.repository.UserRepository;
import com.akerke.salonservice.domain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("name", "surname", "8 777 8888 77 55", Gender.MALE, "email@gmail.com");
        user = new User();
        user.setId(1L);
    }

    @Test
    void delete_whenValidUserId_thenDeleteUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void delete_whenInvalidUserId_thenThrowException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        var id = 1L;
        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> userService.delete(id));

        assertEquals("User with id: %d not found".formatted(id), entityNotFoundException.getMessage());
    }

    @Test
    void getById_whenValidUserId_thenReturnUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var returnedUser = userService.getById(user.getId());

        assertEquals(returnedUser, user);
    }

    @Test
    void getById_whenInvalidUserId_thenThrowException() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> userService.getById(user.getId()));

        assertEquals("User with id: %d not found".formatted(user.getId()), entityNotFoundException.getMessage());
    }

    @Test
    void getAll_whenRepositoryReturnEmptyList_thenReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        var users = userService.getAll();

        assertThat(users).hasSize(0);
    }

    @Test
    void getAll_whenRepositoryReturnList_thenReturnSameList() {
        var users = List.of(new User(), new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        var returnedUsers = userService.getAll();

        assertEquals(users, returnedUsers);
    }

    @Test
    void save_whenValidDTO_thenSaveReturnSavedUser() {
        when(userMapper.toModel(userDTO)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        var savedUser = userService.save(userDTO);

        assertEquals(savedUser, user);
        verify(userRepository).save(user);
    }

    @Test
    void save_whenNullDTO_thenReturnNull() {
        var savedUser = userService.save(null);

        assertNull(savedUser);
    }

    @Test
    void update_whenUserExists_thenUpdateUser() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.update(userDTO, id);

        verify(userMapper, times(1)).update(userDTO, user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update_whenUserDoesNotExist_thenThrowException() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.update(userDTO, id));
        verify(userRepository, never()).save(any(User.class));
    }
}