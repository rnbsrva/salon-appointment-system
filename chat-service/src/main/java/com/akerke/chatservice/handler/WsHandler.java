package com.akerke.chatservice.handler;

import com.akerke.chatservice.mapper.MessageMapper;
import com.akerke.chatservice.payload.request.MessageRequest;
import com.akerke.chatservice.repository.MessageRepository;
import com.akerke.chatservice.service.ChatService;
import com.akerke.chatservice.service.MessageService;
import com.akerke.chatservice.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.BiFunction;

import static com.akerke.chatservice.constants.AppConstants.MESSAGE_TOPIC;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WsHandler {

    private final SimpMessagingTemplate ws;
    private final MessageService messages;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserStatusService userStatusService;

    @MessageMapping("/on_message")
    void onMessage(
            MessageRequest req
    ) {
        var message = messageMapper.toModel(req);
        messageRepository.save(message);
        if (req.fromStuff()) {
            if (userStatusService.isOnline(req.userId())) {
                log.info("send message to user {}", req);
                ws.convertAndSend("/topic/user/message/" + req.userId(), message);
            }
        } else if (userStatusService.supportChatIsOnline(req.salonId())) {
            log.info("send message to staff {}", req);
            ws.convertAndSend("/topic/staff/message/" + req.salonId());
        } else {
            log.info("save message to redis {}", req);
            var key = chatMessagesKey.apply(req.salonId(), req.userId());
            messages.save(message, key);
        }
    }

    private final BiFunction<Long, Long, String> chatMessagesKey = (salonId, userId) ->
            MESSAGE_TOPIC
                    .concat(":" + salonId)
                    .concat(":" + userId);


}
