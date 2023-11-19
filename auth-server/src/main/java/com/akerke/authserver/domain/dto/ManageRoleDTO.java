package com.akerke.authserver.domain.dto;


public record ManageRoleDTO(
        String email,
        Boolean toAdd
) {
}
