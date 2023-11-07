package com.akerke.chatservice.common.handler;

import com.akerke.chatservice.domain.request.SessionRequest;
import com.akerke.chatservice.domain.service.MessageService;
import com.akerke.chatservice.domain.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.BiFunction;


@Slf4j
@RestController
@RequiredArgsConstructor
public class WsSessionHandler {

    private final UserStatusService statusService;
    private final MessageService messageService;
    private final WsMessageHandler wsMessageHandler;

    @MessageMapping("/on_user_session_started")
    void onUserSessionStarted(
            SessionRequest req
    ) {
        log.info("user [id: {}]session started", req.userId());

        req.salons().forEach(salonId -> {
            var messages = messageService.get(chatKey.apply(salonId, req.userId()));

            if (messages.isEmpty()) {
                return;
            }

            messages.forEach(message -> {
                        if (message.getFromStuff()) {
                            var key = chatKey.apply(salonId, req.userId());
                            messageService.delete(key, message);
                            message.setReceived(true);
                            messageService.save(message, key);
                        }
                        wsMessageHandler.sendToUser(message, req.userId());
                    }
            );

        });

        statusService.setOnline(req.userId());
    }

    @MessageMapping("/on_staff_session_started")
    void onStaffSessionStarted(
            SessionRequest req
    ) {
        log.info("staff [{}]session started", req);
        req.salons().forEach(salonId -> statusService.setOnline(salonId, req.userId()));

        req.salons().forEach(salonId -> {
            var messages = messageService.get(chatKey.apply(salonId, req.userId()));

            if (messages.isEmpty()) {
                return;
            }

            messages.forEach(message -> {
                        if (!message.getFromStuff()) {
                            var key = chatKey.apply(salonId, req.userId());
                            messageService.delete(key, message);
                            message.setReceived(true);
                            messageService.save(message, key);
                        }
                        wsMessageHandler.sendToStaff(message, req.userId());
                    }
            );

        });
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

        req.salons().forEach(
                salonId -> statusService.setOffline(salonId, req.userId())
        );
    }

    private final BiFunction<Long, Long, String> chatKey = WsMessageHandler::chatMessagesKey;
    ;
}
