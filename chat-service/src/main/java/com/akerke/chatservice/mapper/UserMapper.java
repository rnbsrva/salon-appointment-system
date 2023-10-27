package com.akerke.chatservice.mapper;

import com.akerke.chatservice.model.User;
import com.akerke.chatservice.payload.request.UserDetailsRequest;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toModel(UserDetailsRequest request);

}
