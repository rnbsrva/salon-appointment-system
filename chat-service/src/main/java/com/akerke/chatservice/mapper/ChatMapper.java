package com.akerke.chatservice.mapper;

import com.akerke.chatservice.model.Chat;
import com.akerke.chatservice.payload.request.ChatCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(
        uses = {
                UserMapper.class
        },
        imports = {
                LocalDateTime.class
        }
)
public interface ChatMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Chat toModel(ChatCreateRequest createRequest);

}
