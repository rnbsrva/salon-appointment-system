package com.akerke.chatservice.domain.mapper;

import com.akerke.chatservice.domain.model.ChatMessage;
import com.akerke.chatservice.domain.request.MessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Supplier;

@Mapper
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "received", expression = "java(false)")
    @Mapping(target = "time", expression = "java(currentTime.get())")
    ChatMessage toModel(MessageRequest req);

    Supplier<Date> currentTime = () -> new Date(System.currentTimeMillis());

}
