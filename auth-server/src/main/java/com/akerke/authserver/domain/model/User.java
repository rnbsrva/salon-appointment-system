package com.akerke.authserver.domain.model;

import com.akerke.authserver.common.constants.SecurityRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user_")
@Getter
@Setter
@ToString
public class User {

    @Id
    private String id;

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;

    private List<SecurityRole> roles;
}
