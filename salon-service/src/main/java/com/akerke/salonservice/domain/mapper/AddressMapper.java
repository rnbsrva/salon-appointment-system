package com.akerke.salonservice.domain.mapper;

import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.entity.Address;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AddressMapper {

    Address toModel(AddressDTO addressDTO);

    AddressDTO toDTO(Address address);

    void update(AddressDTO addressDTO, @MappingTarget Address address);

}
