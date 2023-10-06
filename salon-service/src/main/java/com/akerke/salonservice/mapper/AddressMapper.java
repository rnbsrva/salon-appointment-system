package com.akerke.salonservice.mapper;

import com.akerke.salonservice.constants.Status;
import com.akerke.salonservice.dto.AddressDTO;
import com.akerke.salonservice.entity.Address;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AddressMapper {

    Address toModel (AddressDTO addressDTO);

    AddressDTO toDTO (Address address);

    void update (AddressDTO addressDTO, @MappingTarget Address address);

}
