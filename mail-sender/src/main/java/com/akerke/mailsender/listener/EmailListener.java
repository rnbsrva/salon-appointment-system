package com.akerke.mailsender.listener;

import com.akerke.mailsender.dto.EmailDetails;
import com.akerke.mailsender.service.EmailService;
import com.akerke.mailsender.utils.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {

    private final EmailService emailService;

    @KafkaListener(topics = {
            "email_verification"
    }, groupId = "0")
    void listenVerification(EmailDetails emailDetails) {
        log.info("new email sending {}", emailDetails.toString());
        emailService.sendSimpleMail(emailDetails, MessageType.CONFIRM_EMAIL);
    }

    @KafkaListener(
            topics = "reset_password",
            groupId = "0"
    )
    void listerResetPassword(
            EmailDetails emailDetails
    ) {
        log.info("new reset password request");
        emailService.sendSimpleMail(emailDetails, MessageType.RESET_PASSWORD);
    }

}
