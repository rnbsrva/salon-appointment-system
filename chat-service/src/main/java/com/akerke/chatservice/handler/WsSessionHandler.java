package com.akerke.chatservice.handler;

import com.akerke.chatservice.payload.request.StaffSessionStartRequest;
import com.akerke.chatservice.payload.request.UserSessionStartRequest;
import com.akerke.chatservice.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WsSessionHandler {

    private final UserStatusService statusService;

    @MessageMapping("on_user_session_started")
    void onUserSessionStarted(
            UserSessionStartRequest req
    ) {
        log.info("user [id: {}]session started", req.userId());
        statusService.setOnline(req.userId());
    }

    @MessageMapping("on_staff_session_started")
    void onStaffSessionStarted(
            StaffSessionStartRequest req
    ) {
        log.info("staff [{}]session started", req);
        req.salons().forEach(salonId -> statusService.setOnline(salonId, req.userId()));
    }

    @MessageMapping("on_user_session_closed")
    void onUserSessionClosed(
            UserSessionStartRequest req
    ) {
        log.info("user [id: {}]session closed", req.userId());
        statusService.setOffline(req.userId());
    }

    @MessageMapping("on_staff_session_closed")
    void onStaffSessionClosed(
            StaffSessionStartRequest req
    ) {
        log.info("staff [{}]session closed", req);
        req.salons().forEach(salonId -> statusService.setOffline(salonId, req.userId()));
    }
}
