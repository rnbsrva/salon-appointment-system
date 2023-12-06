package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.constants.AppConstants;
import com.akerke.salonservice.infrastructure.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.infrastructure.kafka.KafkaProducer;
import com.akerke.salonservice.domain.service.ManagerService;
import com.akerke.salonservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {


    private final UserService userService;
    private final KafkaProducer kafkaProducer;

    @Override
    public void add(Long userId, Long salonId) {

        var user = userService.getById(userId);

//        kafkaProducer.produce(
//                AppConstants.MANAGER_TOPIC_NAME,
//                new KafkaManageRoleRequest(salonId, user.getEmail(), true)
//        );
    }

    @Override
    public void remove(Long userId, Long salonId) {

        var user = userService.getById(userId);

//        kafkaProducer.produce(
//                AppConstants.MANAGER_TOPIC_NAME,
//                new KafkaManageRoleRequest(salonId, user.getEmail(), false)
//        );
    }
}
