package com.akerke.authserver.domain.mapper;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        imports = {
                SecurityRole.class,
                List.class
        }
)
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "emailConfirmed" , expression = "java(false)")
    @Mapping(target = "roles", expression = "java(List.of(SecurityRole.USER))")
    User toModel(RegistrationDTO registrationDTO);

}
