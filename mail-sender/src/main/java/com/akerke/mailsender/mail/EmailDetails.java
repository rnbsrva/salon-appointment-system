package com.akerke.mailsender.mail;

public record EmailDetails (
     String recipient,
     String msgBody,
     String subject,
     String attachment){

}