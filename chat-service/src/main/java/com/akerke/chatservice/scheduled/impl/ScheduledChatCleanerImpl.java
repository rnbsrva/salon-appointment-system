package com.akerke.chatservice.scheduled.impl;

import com.akerke.chatservice.scheduled.ScheduledChatCleaner;
import com.akerke.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledChatCleanerImpl implements ScheduledChatCleaner {

    private final ChatService chatService;

    @Scheduled(cron = "${scheduled.clean-chat.cron}")
    @Override
    public void clean() {
        var thresholdDateTime = Mono.just(LocalDateTime.now().minusMonths(1));

        chatService
                .findChatsByCreatedAtBefore(thresholdDateTime)
                .flatMap(chatService::deleteChat)
                .subscribe();
    }


}
