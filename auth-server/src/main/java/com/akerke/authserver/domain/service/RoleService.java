package com.akerke.authserver.domain.service;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.dto.ManageRoleDTO;
import com.akerke.authserver.domain.dto.StatusResponseDTO;

public interface RoleService {
    StatusResponseDTO makeChanges(ManageRoleDTO manageRoleDTO,SecurityRole securityRole);
}
