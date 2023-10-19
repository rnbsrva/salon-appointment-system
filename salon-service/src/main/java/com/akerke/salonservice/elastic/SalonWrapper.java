package com.akerke.salonservice.elastic;

import com.akerke.salonservice.entity.Address;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

public class SalonWrapper {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private Address address;
    private String description;

}
