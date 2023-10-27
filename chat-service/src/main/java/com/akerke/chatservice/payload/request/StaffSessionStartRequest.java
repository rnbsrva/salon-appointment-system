package com.akerke.chatservice.payload.request;

import java.util.List;

public record StaffSessionStartRequest(
        Long userId,
        List<Long> salons
) {
}
