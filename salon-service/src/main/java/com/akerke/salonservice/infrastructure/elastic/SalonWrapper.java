package com.akerke.salonservice.infrastructure.elastic;

import com.akerke.salonservice.domain.entity.Address;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "salon_idx")
public class SalonWrapper {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private Address address;
    private String description;
    private List<String> treatments;

}
