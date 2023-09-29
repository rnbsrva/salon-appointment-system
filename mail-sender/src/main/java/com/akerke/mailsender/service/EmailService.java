package com.akerke.mailsender.service;

import com.akerke.mailsender.dto.EmailDetails;
import com.akerke.mailsender.utils.MessageType;
import reactor.core.publisher.Mono;

public interface EmailService {

    Mono<Void> sendSimpleMail(EmailDetails details, MessageType messageType);

    Mono<Void> sendMailWithAttachment(EmailDetails details);
}
