package com.akerke.mailsender.listener;

import com.akerke.mailsender.dto.EmailDetails;
import com.akerke.mailsender.service.EmailService;
import com.akerke.mailsender.utils.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {

    private final EmailService emailService;

    @RetryableTopic(
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "false",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    @KafkaListener(topics = "email_verification", groupId = "0")
    void listenVerification(EmailDetails emailDetails) {
        log.info("new email sending {}", emailDetails.toString());
        emailService.sendSimpleMail(emailDetails, MessageType.CONFIRM_EMAIL);
    }

    @RetryableTopic(
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "false",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(topics = "reset_password", groupId = "0")
    void listerResetPassword(EmailDetails emailDetails) {
        log.info("new reset password request");
        emailService.sendSimpleMail(emailDetails, MessageType.RESET_PASSWORD);
    }

    @DltHandler
    public void dlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info(in + " from " + topic);
    }

}
