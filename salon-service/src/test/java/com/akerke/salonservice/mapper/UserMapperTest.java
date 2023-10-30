package com.akerke.salonservice.mapper;

import com.akerke.salonservice.common.constants.Gender;
import com.akerke.salonservice.domain.mapper.UserMapper;
import com.akerke.salonservice.domain.dto.UserDTO;
import com.akerke.salonservice.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {
    private UserMapper userMapper;
    private User user;
    private UserDTO userDTO;


        @BeforeEach
        void setUp(){
            userMapper = Mappers.getMapper(UserMapper.class);
            userDTO = new UserDTO("name", "surname", "phone", Gender.FEMALE, "email");
            user = new User();
        }

        @Test
        void testToModelWhenUserDtoGivenThenReturnUser(){
            User result = userMapper.toModel(userDTO);
            assertNotNull(result);
            assertEquals(userDTO.name(), result.getName());
            assertEquals(userDTO.surname(), result.getSurname());
            assertEquals(userDTO.phone(), result.getPhone());
            assertEquals(userDTO.gender(), result.getGender());
            assertEquals(userDTO.email(), result.getEmail());
        }

        @Test
        void testToDtoWhenUserGivenThenReturnUserDto(){
            user.setName("name");
            user.setSurname("surname");
            user.setPhone("phone");
            user.setGender(Gender.FEMALE);
            user.setEmail("email");

            UserDTO result = userMapper.toDTO(user);
            assertNotNull(result);
            assertEquals(user.getName(), result.name());
            assertEquals(user.getSurname(), result.surname());
            assertEquals(user.getPhone(), result.phone());
            assertEquals(user.getGender(), result.gender());
            assertEquals(user.getEmail(), result.email());
        }

        @Test
        void testUpdateWhenNullUserDtoGivenThenNoChangesAreMade(){
            User originalUser = new User();
            userMapper.update(null, user);
            assertEquals(originalUser.getName(), user.getName());
            assertEquals(originalUser.getPhone(), user.getPhone());
            assertEquals(originalUser.getGender(), user.getGender());
            assertEquals(originalUser.getEmail(), user.getEmail());
            assertEquals(originalUser.getSurname(), user.getSurname());
            assertEquals(originalUser.getAppointments(), user.getAppointments());
            assertEquals(originalUser.getId(), user.getId());
            assertEquals(originalUser.getSalons(), user.getSalons());
        }

        @Test
        void testUpdateWhenNullUserGivenThenThrowsNullPointerException(){
            assertThrows(NullPointerException.class, ()->userMapper.update(userDTO, null));
        }

}
