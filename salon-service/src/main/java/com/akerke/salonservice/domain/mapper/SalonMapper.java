package com.akerke.salonservice.domain.mapper;

import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.entity.*;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapper;
import org.mapstruct.*;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.ArrayList;
import java.util.List;

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

    default List<SalonWrapper> toListWrapperFromHit(SearchHits<SalonWrapper> searchHits){
        return searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();
    };


}
