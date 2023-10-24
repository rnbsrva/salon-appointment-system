package com.akerke.authservice.repository;

import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.domain.repository.UserRepository;
import com.akerke.authservice.tc.PostgresTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class UserRepositoryTest extends PostgresTestContainer {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@test.com");
        user.setPhone("1234567890");
    }

    @Test
    public void testFindByEmailWhenEmailExistsThenReturnUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void testFindByEmailWhenEmailDoesNotExistThenReturnEmptyOptional() {
        var nonExistEmail = "nonexistent@test.com";

        when(userRepository.findByEmail(nonExistEmail)).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByEmail(nonExistEmail);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail(nonExistEmail);
    }

    @Test
    public void testFindByEmailOrPhoneWhenEmailOrPhoneExistsThenReturnUser() {
        when(userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getPhone(), foundUser.get().getPhone());
        verify(userRepository, times(1)).findByEmailOrPhone(user.getEmail(), user.getPhone());
    }

    @Test
    public void testFindByEmailOrPhoneWhenEmailAndPhoneDoesNotExistThenReturnEmptyOptional() {
        var nonExistEmail = "nonexistent@test.com";
        var nonExistPhone = "0987654321";
        when(userRepository.findByEmailOrPhone(nonExistEmail, nonExistPhone)).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByEmailOrPhone(nonExistEmail, nonExistPhone);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmailOrPhone(nonExistEmail, nonExistPhone);
    }

    @Test
    public void testFindByEmailOrPhoneWhenEmailExistsButPhoneDoesNotExistThenReturnUser() {
        var nonExistPhone = "0987654321";
        when(userRepository.findByEmailOrPhone(user.getEmail(), nonExistPhone)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmailOrPhone(user.getEmail(), nonExistPhone);

        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmailOrPhone(user.getEmail(), nonExistPhone);
    }

    @Test
    public void testFindByEmailOrPhoneWhenPhoneExistsButEmailDoesNotExistThenReturnUser() {
        var nonExistEmail = "nonexistent@test.com";
        when(userRepository.findByEmailOrPhone(nonExistEmail, user.getPhone())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmailOrPhone(nonExistEmail, user.getPhone());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getPhone(), foundUser.get().getPhone());
        verify(userRepository, times(1)).findByEmailOrPhone(nonExistEmail, user.getPhone());
    }
}