package com.akerke.salonservice.entity;

import com.akerke.salonservice.constants.Gender;
import com.akerke.salonservice.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String phone;
    private Gender gender;
    private String email;

    @OneToMany(
            mappedBy = "users",
            cascade = CascadeType.ALL
    )
    private List<Appointment> appointments;

    public User(String name, String surname, String phone, Gender gender, String email) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
    }
}
