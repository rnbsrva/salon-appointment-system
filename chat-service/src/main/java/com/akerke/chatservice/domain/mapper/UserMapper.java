package com.akerke.chatservice.domain.mapper;

import com.akerke.chatservice.domain.model.User;
import com.akerke.chatservice.domain.request.UserDetailsRequest;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toModel(UserDetailsRequest request);

}
