package com.akerke.salonservice.dto;

import com.akerke.salonservice.constants.Gender;

public record UserDTO (
     String name,
     String surname,
     String phone,
     Gender gender,
     String email
) {
}
