package com.akerke.authservice.mapper;

import com.akerke.authservice.constants.PermissionType;
import com.akerke.authservice.entity.Role;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        imports = PermissionType.class
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toModel(RegistrationRequest req, List<Role> roles);

}
