package com.akerke.chatservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private Boolean online;
    private Date lastOnlineTime;
    private String name;
    private String surname;
}
