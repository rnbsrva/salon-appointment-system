package com.akerke.authservice.mapper;

import com.akerke.authservice.constants.Scope;
import com.akerke.authservice.entity.User;
import com.akerke.authservice.payload.request.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(
        imports = Scope.class
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "emailVerified" , expression = "java(false)")
    User toModel(RegistrationRequest req);

}
