package com.akerke.salonservice.domain.mapper;

import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.entity.*;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapper;
import org.elasticsearch.search.SearchHit;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mapper(imports = {Master.class, ArrayList.class,
        Treatment.class, WorkDay.class, Feedback.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SalonMapper {

    @Mapping(target = "masters", expression = "java(new ArrayList<Master>())")
    @Mapping(target = "treatments", expression = "java(new ArrayList<Treatment>())")
    @Mapping(target = "workDays", expression = "java(new ArrayList<WorkDay>())")
    Salon toModel(SalonDTO salonDTO);

    @Mapping(target = "ownerId", expression = "java(salon.getOwner().getId())")
    SalonDTO toDTO (Salon salon);

    @Mapping(target = "id", ignore = true)
    void update(SalonDTO salonDTO, @MappingTarget Salon salon);

    default List<SalonWrapper> toListWrapperFromHit(org.elasticsearch.search.SearchHit[] searchHits){
        return Arrays.stream(searchHits)
                .map(SearchHit::getSourceAsMap)
                .map(this::mapToSalonWrapper)
                .toList();
    };

    private SalonWrapper mapToSalonWrapper(Map<String, Object> sourceMap) {
        SalonWrapper salon = new SalonWrapper();

        salon.setId(Long.parseLong(sourceMap.get("id").toString()));
        salon.setName(sourceMap.get("name").toString());
        salon.setPhone(sourceMap.get("phone").toString());
        salon.setEmail(sourceMap.get("email").toString());

        // Map the 'address' field to the 'Address' entity
        Map<String, Object> addressMap = (Map<String, Object>) sourceMap.get("address");
        Address address = new Address();
        address.setId(Long.parseLong(addressMap.get("id").toString()));
        address.setHouseNumber(Long.parseLong(addressMap.get("houseNumber").toString()));
        address.setStreet(addressMap.get("street").toString());
        address.setCity(addressMap.get("city").toString());
        address.setState(addressMap.get("state").toString());
        salon.setAddress(address);

        salon.setDescription(sourceMap.get("description").toString());

        // Map the 'treatments' field to a list
        List<String> treatments = (List<String>) sourceMap.get("treatments");
        salon.setTreatments(treatments);

        return salon;
    }

}
