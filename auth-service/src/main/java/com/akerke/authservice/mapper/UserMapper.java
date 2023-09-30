package com.akerke.authservice.mapper;

import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toModel(RegistrationRequest req);

}
