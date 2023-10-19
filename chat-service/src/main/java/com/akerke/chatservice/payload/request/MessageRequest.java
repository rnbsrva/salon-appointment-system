package com.akerke.chatservice.payload.request;

import java.util.List;

public record MessageRequest(
        String content,
        Boolean fromStuff,
        List<Long> fileIdList,
        String chatId
) {
}
