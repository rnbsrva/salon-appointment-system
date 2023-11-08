package com.akerke.qrservice.domain.entity;

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

    public QR(String link) {
        this.link = link;
    }
}
