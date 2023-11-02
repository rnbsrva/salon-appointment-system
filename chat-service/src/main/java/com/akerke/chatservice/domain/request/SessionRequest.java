package com.akerke.chatservice.domain.request;

import java.util.List;

public record SessionRequest(
        Long userId,
        List<Long> salons
) {
}
