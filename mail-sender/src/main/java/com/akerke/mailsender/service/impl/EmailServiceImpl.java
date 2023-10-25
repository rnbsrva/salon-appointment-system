package com.akerke.mailsender.service.impl;

import com.akerke.mailsender.dto.EmailDetails;
import com.akerke.mailsender.service.EmailService;
import com.akerke.mailsender.utils.MessageType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final Configuration ftl;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public Mono<Void> sendSimpleMail(
            EmailDetails details,
            MessageType messageType
    ) {
        return Mono.fromRunnable(() -> {
            var msg = javaMailSender.createMimeMessage();
            try {
                var helper = new MimeMessageHelper(
                        msg,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name()
                );

                var template = ftl.getTemplate(messageType.getTemplate());

                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, Map.of(
                        "link", details.msgBody()
                ));

                helper.setText(html, true);
                helper.setTo(details.recipient());
                helper.setFrom(sender);
                helper.setSubject(details.subject());

                javaMailSender.send(msg);

                log.info("Mail Sent Successfully");
            } catch (Exception e) {
                log.error("Error while Sending Mail, msg {}", e.getMessage());
            }
        });
    }


    @Override
    public Mono<Void> sendMailWithAttachment(EmailDetails details) {
        return Mono.fromRunnable(() -> {

            var mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper;

            try {
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(details.recipient());
                mimeMessageHelper.setText(details.msgBody());
                mimeMessageHelper.setSubject(details.subject());

                FileSystemResource file = new FileSystemResource(new File(details.attachment()));

                mimeMessageHelper.addAttachment(file.getFilename(), file);

                javaMailSender.send(mimeMessage);
                log.info("Mail Sent Successfully");
            } catch (MessagingException e) {
                log.error("Error while Sending Mail: msg {}", e.getMessage());
            }
        });
    }
}

