package com.akerke.chatservice.common.handler;

import com.akerke.chatservice.domain.mapper.MessageMapper;
import com.akerke.chatservice.domain.model.ChatMessage;
import com.akerke.chatservice.domain.request.MessageRequest;
import com.akerke.chatservice.domain.repository.MessageRepository;
import com.akerke.chatservice.domain.service.MessageService;
import com.akerke.chatservice.domain.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import static com.akerke.chatservice.common.constants.AppConstants.MESSAGE_TOPIC;

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

    private void logAndSendToUser(MessageRequest req, ChatMessage chatMessage) {
        log.info("send message to user {}", req);
        ws.convertAndSend("/topic/user/message/" + req.userId(), chatMessage);
    }

    private void logAndSendToStaff(MessageRequest req, ChatMessage chatMessage) {
        log.info("send message to staff {}", req);
        ws.convertAndSend("/topic/staff/message/" + req.salonId(), chatMessage);
    }

    private void logAndSaveToRedis(MessageRequest req, ChatMessage chatMessage) {
        log.info("save message to redis {}", req);
        var key = chatMessagesKey(req.salonId(), req.userId());
        messageService.save(chatMessage, key);
    }

    void sendToUser(ChatMessage chatMessage, Long userId) {
        ws.convertAndSend("/topic/user/message/" + userId, chatMessage);
    }

    void sendToStaff(ChatMessage chatMessage, Long salonId) {
        ws.convertAndSend("/topic/staff/message/" + salonId, chatMessage);
    }

    public static String chatMessagesKey(Long salonId, Long userId) {
        return MESSAGE_TOPIC + ":" + salonId + ":" + userId;
    }


}

