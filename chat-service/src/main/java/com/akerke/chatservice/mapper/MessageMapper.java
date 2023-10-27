package com.akerke.chatservice.mapper;

import com.akerke.chatservice.model.Message;
import com.akerke.chatservice.payload.request.MessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Mapper
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "received", expression = "java(false)")
    @Mapping(target = "time", expression = "java(currentTime.get())")
    Message toModel(MessageRequest req);

    Supplier<LocalDateTime> currentTime = LocalDateTime::now;

}
