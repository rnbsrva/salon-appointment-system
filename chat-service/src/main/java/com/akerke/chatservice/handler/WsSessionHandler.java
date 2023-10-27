package com.akerke.chatservice.handler;

import com.akerke.chatservice.payload.request.SessionRequest;
import com.akerke.chatservice.service.MessageService;
import com.akerke.chatservice.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WsSessionHandler {

    private final UserStatusService statusService;
    private final MessageService messageService;

    @MessageMapping("/on_user_session_started")
    void onUserSessionStarted(
            SessionRequest req
    ) {
        log.info("user [id: {}]session started", req.userId());
        statusService.setOnline(req.userId());
    }

    @MessageMapping("/on_staff_session_started")
    void onStaffSessionStarted(
            SessionRequest req
    ) {
        log.info("staff [{}]session started", req);
        req.salons().forEach(salonId -> statusService.setOnline(salonId, req.userId()));
    }

    @MessageMapping("/on_user_session_closed")
    void onUserSessionClosed(
            SessionRequest req
    ) {
        log.info("user [id: {}]session closed", req.userId());
        statusService.setOffline(req.userId());
    }

    @MessageMapping("/on_staff_session_closed")
    void onStaffSessionClosed(
            SessionRequest req
    ) {
        log.info("staff [{}]session closed", req);
        req.salons().forEach(salonId -> statusService.setOffline(salonId, req.userId()));
    }
}
