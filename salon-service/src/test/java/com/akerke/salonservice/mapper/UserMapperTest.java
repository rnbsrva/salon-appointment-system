package com.akerke.salonservice.mapper;

import com.akerke.salonservice.constants.Gender;
import com.akerke.salonservice.dto.UserDTO;
import com.akerke.salonservice.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserMapperTest {
    private UserMapper userMapper;
    private User user;
    private UserDTO userDTO;

    void setUp(){
        userMapper = Mappers.getMapper(UserMapper.class);
        userDTO = new UserDTO("name", "surname", "phone", Gender.FEMALE, "email");
        user = new User();
    }

    @Test
    void update_WhenUserDtoIsNull_ThenNoChangesAreMade(){
        assertDoesNotThrow(()->userMapper.update(null, user));
    }

    @Test
    void update_WhenUserIsNull_ThenThrowsNullPointerException(){
        assertThrows(NullPointerException.class, ()->userMapper.update(userDTO, null));
    }

}
