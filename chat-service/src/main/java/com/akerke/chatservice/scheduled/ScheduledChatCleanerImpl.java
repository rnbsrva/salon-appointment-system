package com.akerke.chatservice.scheduled;

import com.akerke.chatservice.repository.ChatReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledChatCleanerImpl implements ScheduledChatCleaner {

    private final ChatReactiveRepository chatReactiveRepository;

    @Scheduled(cron = "${scheduled.clean-chat.cron}")
    @Override
    public void clean() {
        var thresholdDateTime = Mono.just(LocalDateTime.now());

        chatReactiveRepository
                .findChatsByCreatedAtBefore(thresholdDateTime)
                .flatMap(chat -> chatReactiveRepository.delete(chat))
                .subscribe();
    }


}
