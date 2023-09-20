package com.akerke.mailsender.service;

import com.akerke.mailsender.dto.EmailDetails;
import com.akerke.mailsender.utils.MessageType;

public interface EmailService {

    void sendSimpleMail(EmailDetails details, MessageType messageType);

    void sendMailWithAttachment(EmailDetails details);
}
