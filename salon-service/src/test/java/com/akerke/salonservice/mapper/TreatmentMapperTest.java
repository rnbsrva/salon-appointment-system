package com.akerke.salonservice.mapper;

import com.akerke.salonservice.common.constants.TreatmentType;
import com.akerke.salonservice.domain.mapper.TreatmentMapper;
import com.akerke.salonservice.domain.dto.TreatmentDTO;
import com.akerke.salonservice.domain.entity.Treatment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class TreatmentMapperTest {

    private TreatmentMapper treatmentMapper;
    private Treatment treatment;
    private TreatmentDTO treatmentDTO;

    @BeforeEach
    public void setUp() {
        treatmentMapper = Mappers.getMapper(TreatmentMapper.class);
        treatmentDTO = new TreatmentDTO(1L, "Test Treatment", 100L, 60L, TreatmentType.BROWS );
        treatment = new Treatment();
        treatment.setName("Test Treatment");
        treatment.setPrice(100L);
        treatment.setMinutes(60L);
        treatment.setTreatmentType(TreatmentType.LASHES);
    }

    @Test
    void update_WhenAppointmentDtoIsNull_thenNoChangesAreMade(){
        assertDoesNotThrow(()->treatmentMapper.update(null, treatment));
    }

    @Test
    void update_WhenAppointmentIsNull_thenNullPointerExceptionIsThrown(){
        assertThrows(NullPointerException.class, ()->treatmentMapper.update(treatmentDTO, null));
    }

    @Test
    public void toModel_WhenValidTreatmentDTOThenReturnTreatment() {

        treatment = treatmentMapper.toModel(treatmentDTO);

        assertEquals(treatmentDTO.name(), treatment.getName());
        assertEquals(treatmentDTO.price(), treatment.getPrice());
        assertEquals(treatmentDTO.minutes(), treatment.getMinutes());
        assertEquals(treatmentDTO.treatmentType(), treatment.getTreatmentType().toString());
    }

    @Test
    public void toDTO_WhenValidTreatmentThenReturnTreatmentDTO() {

        treatmentDTO = treatmentMapper.toDTO(treatment);

        assertEquals(treatment.getName(), treatmentDTO.name());
        assertEquals(treatment.getPrice(), treatmentDTO.price());
        assertEquals(treatment.getMinutes(), treatmentDTO.minutes());
        assertEquals(treatment.getTreatmentType(), treatmentDTO.treatmentType());
    }

    @Test
    public void update_WhenValidTreatmentDTOAndTreatmentThenUpdateTreatment() {

        treatmentMapper.update(treatmentDTO, treatment);

        assertEquals(treatment.getName(), treatmentDTO.name());
        assertEquals(treatment.getPrice(), treatmentDTO.price());
        assertEquals(treatment.getMinutes(), treatmentDTO.minutes());
        assertEquals(treatment.getTreatmentType(), treatmentDTO.treatmentType());
    }
}