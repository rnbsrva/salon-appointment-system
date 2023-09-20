package com.akerke.mailsender.dto;

public record EmailDetails (
     String recipient,
     String msgBody,
     String subject,
     String attachment){

}