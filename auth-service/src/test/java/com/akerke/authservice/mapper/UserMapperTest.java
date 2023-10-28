package com.akerke.authservice.mapper;

import com.akerke.authservice.domain.mapper.UserMapper;
import com.akerke.authservice.domain.payload.request.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private UserMapper userMapper;

    private RegistrationRequest request;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
        request = new RegistrationRequest(
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                "password"
        );
    }

    @Test
    public void toModel_shouldReturnValidModel_whenRequestAndEncodedPasswordNonNull() {
        var encodedPassword = "encodedPassword";

        var user = userMapper.toModel(request, encodedPassword);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(request.name());
        assertThat(user.getSurname()).isEqualTo(request.surname());
        assertThat(user.getEmail()).isEqualTo(request.email());
        assertThat(user.getPhone()).isEqualTo(request.phone());
        assertThat(user.getPassword()).isEqualTo(encodedPassword);
        assertThat(user.getEmailVerified()).isFalse();
    }

    @Test
    public void toModel_shouldReturnNullUser_wenNullRequestAndNullEncodedPassword() {
        RegistrationRequest request = null;
        String encodedPassword = null;

        var user = userMapper.toModel(request, encodedPassword);

        assertThat(user).isNull();
    }

    @Test
    public void toModel_shouldReturnUserWithNullPassword_whenNullPassword() {
        String encodedPassword = null;

        var user = userMapper.toModel(request, encodedPassword);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(request.name());
        assertThat(user.getSurname()).isEqualTo(request.surname());
        assertThat(user.getEmail()).isEqualTo(request.email());
        assertThat(user.getPhone()).isEqualTo(request.phone());
        assertThat(user.getPassword()).isNull();
        assertThat(user.getEmailVerified()).isFalse();
    }
}