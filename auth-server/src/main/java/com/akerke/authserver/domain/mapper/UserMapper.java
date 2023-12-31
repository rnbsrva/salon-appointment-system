package com.akerke.authserver.domain.mapper;

import com.akerke.authserver.common.constants.SecurityRole;
import com.akerke.authserver.domain.dto.RegistrationDTO;
import com.akerke.authserver.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@Mapper(
        imports = {
                SecurityRole.class,
                List.class,
                LocalDateTime.class
        }
)
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "otp" , expression = "java(otp)")
    @Mapping(target = "emailConfirmed" , expression = "java(false)")
    @Mapping(target = "registeredTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "otpCreationTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "roles", expression = "java(List.of(SecurityRole.USER))")
    User toModel(RegistrationDTO registrationDTO,Integer otp);

}
