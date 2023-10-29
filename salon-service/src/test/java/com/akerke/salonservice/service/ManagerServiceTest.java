package com.akerke.salonservice.service;

import com.akerke.salonservice.constants.AppConstants;
import com.akerke.salonservice.entity.User;
import com.akerke.salonservice.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.kafka.KafkaProducer;
import com.akerke.salonservice.service.impl.ManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private ManagerServiceImpl managerService;

    private final Long userId = 1L;
    private final Long salonId = 1L;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(userId);
        user.setEmail("email@gmail.com");
    }

    @Test
    void add() {
        when(userService.getById(userId)).thenReturn(user);

        managerService.add(userId, salonId);

        verify(userService).getById(userId);
        verify(kafkaProducer).produce(AppConstants.MANAGER_TOPIC_NAME, new KafkaManageRoleRequest(
                salonId,
                user.getEmail(),
                true
        ));
    }

    @Test
    void remove() {
        when(userService.getById(userId)).thenReturn(user);

        managerService.remove(userId, salonId);

        verify(userService).getById(userId);
        verify(kafkaProducer).produce(AppConstants.MANAGER_TOPIC_NAME, new KafkaManageRoleRequest(
                salonId,
                user.getEmail(),
                false
        ));
    }
}