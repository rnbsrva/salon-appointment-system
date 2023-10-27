package com.akerke.chatservice.handler;

import com.akerke.chatservice.mapper.MessageMapper;
import com.akerke.chatservice.model.Message;
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
public class WsMessageHandler {

    private final SimpMessagingTemplate ws;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserStatusService userStatusService;
    private final MessageService messageService;

    @MessageMapping("/on_message")
    void onMessage(MessageRequest req) {
        var message = messageMapper.toModel(req);
        messageRepository.save(message);

        if (req.fromStuff() && userStatusService.isOnline(req.userId())) {
            logAndSendToUser(req, message);
        } else if (userStatusService.supportChatIsOnline(req.salonId())) {
            logAndSendToStaff(req, message);
        } else {
            logAndSaveToRedis(req, message);
        }
    }

    private void logAndSendToUser(MessageRequest req, Message message) {
        log.info("send message to user {}", req);
        ws.convertAndSend("/topic/user/message/" + req.userId(), message);
    }

    private void logAndSendToStaff(MessageRequest req, Message message) {
        log.info("send message to staff {}", req);
        ws.convertAndSend("/topic/staff/message/" + req.salonId(), message);
    }

    private void logAndSaveToRedis(MessageRequest req, Message message) {
        log.info("save message to redis {}", req);
        var key = chatMessagesKey(req.salonId(), req.userId());
        messageService.save(message, key);
    }

    void sendToUser(Message message, Long userId) {
        ws.convertAndSend("/topic/user/message/" + userId, message);
    }

    void sendToStaff(Message message, Long salonId) {
        ws.convertAndSend("/topic/staff/message/" + salonId, message);
    }

    public static String chatMessagesKey(Long salonId, Long userId) {
        return MESSAGE_TOPIC + ":" + salonId + ":" + userId;
    }


}

