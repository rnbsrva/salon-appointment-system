package com.akerke.mailsender.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {

    private final EmailService emailService;

    @KafkaListener(topics = {"email_verification","reset_password"}, groupId = "0")
    void listenVerification(EmailDetails emailDetails){
        log.info("new email sending {}", emailDetails.toString());
        emailService.sendSimpleMail(emailDetails);
    }

}
