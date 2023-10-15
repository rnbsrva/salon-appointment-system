package com.akerke.authservice.kafka;

import com.akerke.authservice.constants.SecurityRole;
import com.akerke.authservice.kafka.KafkaManageRoleRequest;
import com.akerke.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaManageRoleListener {

    private final AuthService authService;

//    @KafkaListener(
//            topics = "manage_role_admin",
//            groupId = "0"
//    )
    void listenManageRoleAdmin(
        KafkaManageRoleRequest kafkaManageRoleRequest
    ){
      authService.manageRole(kafkaManageRoleRequest, SecurityRole.ADMIN);
    }


//    @KafkaListener(
//            topics = "manage_role_manager",
//            groupId = "0"
//    )
    void listenManageRoleManager(
            KafkaManageRoleRequest kafkaManageRoleRequest
    ){
        authService.manageRole(kafkaManageRoleRequest, SecurityRole.MANAGER);
    }

}
