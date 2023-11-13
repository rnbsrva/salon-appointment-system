package com.akerke.authservice.infrastructure.kafka;

import com.akerke.authservice.common.constants.SecurityRole;
import com.akerke.authservice.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaManageRoleListener {

    private final AuthService authService;

//    @KafkaListener(
//            topics = "manage_role_admin",
//            groupId = "0"
//    )todo
    void listenManageRoleAdmin(
        KafkaManageRoleRequest kafkaManageRoleRequest
    ){
      authService.manageRole(kafkaManageRoleRequest, SecurityRole.ADMIN);
    }


//    @KafkaListener(
//            topics = "manage_role_manager",
//            groupId = "0"
//    )todo
    void listenManageRoleManager(
            KafkaManageRoleRequest kafkaManageRoleRequest
    ){
        authService.manageRole(kafkaManageRoleRequest, SecurityRole.MANAGER);
    }

}