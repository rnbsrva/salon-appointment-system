package com.akerke.authservice.domain.mapper;

import com.akerke.authservice.common.constants.Scope;
import com.akerke.authservice.domain.entity.User;
import com.akerke.authservice.domain.payload.request.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(
        imports = Scope.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "emailVerified", expression = "java(false)")
    @Mapping(target = "password", expression = "java(encodedPassword)")
    User toModel(RegistrationRequest req, String encodedPassword);

}
