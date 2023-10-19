package com.akerke.salonservice.elastic;

import com.akerke.salonservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class SalonWrapper {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private Address address;
    private String description;
    private List<String> treatments;

}
