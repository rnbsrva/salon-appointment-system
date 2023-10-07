package com.akerke.authservice.payload.request;

public record ManageRoleRequest (
        Long target,
        String email,
        Boolean add // TODO: 10/7/2023 complete payload body
){
}

