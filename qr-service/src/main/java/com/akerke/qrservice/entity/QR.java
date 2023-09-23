package com.akerke.qrservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("qr")
@Getter
@Setter
public class QR {

    @Id
    private String id;
    private String link;
    private String base64Data;

    public  QR (String link, String base64Data){
        this.link = link;
        this.base64Data = base64Data;
    }
}
