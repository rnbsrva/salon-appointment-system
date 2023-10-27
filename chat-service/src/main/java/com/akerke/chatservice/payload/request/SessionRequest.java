package com.akerke.chatservice.payload.request;

import java.util.List;

public record SessionRequest(
        Long userId,
        List<Long> salons
) {
}
