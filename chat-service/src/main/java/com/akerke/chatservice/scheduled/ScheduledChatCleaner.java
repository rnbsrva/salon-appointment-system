package com.akerke.chatservice.scheduled;

import com.akerke.chatservice.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledChatCleaner {

    private final ChatRepository chatRepository;

    @Scheduled(cron = "${scheduled.clean-chat.cron}")
    public void clean() {
        var monthAgo = LocalDateTime.now().minusMonths(1);

        log.info("staring clean chats time: {}", monthAgo);

        chatRepository.deleteAllByCreatedAtBefore(monthAgo);

        log.info("finished clean chats");
    }

}
