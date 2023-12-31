package com.akerke.chatservice.domain.request;

import java.util.List;

public record MessageRequest(
        String content,
        Boolean fromStuff,
        List<Long> fileIdList,
        Long userId,
        Long salonId
) {
}
