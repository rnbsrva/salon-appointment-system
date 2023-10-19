package com.akerke.salonservice.mapper;

import com.akerke.salonservice.dto.WorkTimeDTO;
import com.akerke.salonservice.entity.WorkDay;
import com.akerke.salonservice.entity.WorkTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WorkTimeMapperTest {

    private WorkTimeMapper workTimeMapper;

    @BeforeEach
    void setUp() {
        workTimeMapper = Mappers.getMapper(WorkTimeMapper.class);
    }

    @Test
    void toModel_whenValidWorkTimeDTO_thenReturnWorkTimeWithSameFields() {
        var workTimeDTO = new WorkTimeDTO(new Date(), new Date(), true, 1L);

        var result = workTimeMapper.toModel(workTimeDTO);

        assertEquals(workTimeDTO.startTime(), result.getStartTime());
        assertEquals(workTimeDTO.endTime(), result.getEndTime());
        assertEquals(workTimeDTO.isBreak(), result.getIsBreak());
    }

    @Test
    void toModel_whenNullWorkTimeDTO_thenReturnNull() {
        var result = workTimeMapper.toModel(null);

        assertNull(result);
    }

    @Test
    void toDTO_whenValidWorkTime_thenReturnWorkTimeDTOWithSameFields() {
        var workTime = new WorkTime(new Date(), new Date(), true, new WorkDay());
        workTime.getWorkDay().setId(1L);

        var result = workTimeMapper.toDTO(workTime);

        assertEquals(workTime.getStartTime(), result.startTime());
        assertEquals(workTime.getEndTime(), result.endTime());
        assertEquals(workTime.getIsBreak(), result.isBreak());
        assertEquals(workTime.getWorkDayId(), result.workDayId());
    }

    @Test
    void toDTO_whenNullWorkTime_thenReturnNull() {
        var result = workTimeMapper.toDTO(null);

        assertNull(result);
    }

    @Test
    void updateWhenValidWorkTimeDTOAndEntity_thenUpdateEntity() {
        var workTimeDTO = new WorkTimeDTO(new Date(), new Date(), true, 1L);
        var workTime = new WorkTime(new Date(), new Date(), false, new WorkDay());

        workTimeMapper.update(workTimeDTO, workTime);

        assertEquals(workTimeDTO.startTime(), workTime.getStartTime());
        assertEquals(workTimeDTO.endTime(), workTime.getEndTime());
        assertEquals(workTimeDTO.isBreak(), workTime.getIsBreak());
    }

    @Test
    void update_whenNullWorkTimeDTOAndValidEntity_thenNoUpdate() {
        var workTime = new WorkTime(new Date(), new Date(), false, new WorkDay());
        var originalWorkTime = new WorkTime(workTime.getStartTime(), workTime.getEndTime(), workTime.getIsBreak(), workTime.getWorkDay());

        workTimeMapper.update(null, workTime);

        assertEquals(originalWorkTime.getStartTime(), workTime.getStartTime());
        assertEquals(originalWorkTime.getEndTime(), workTime.getEndTime());
        assertEquals(originalWorkTime.getIsBreak(), workTime.getIsBreak());
    }

}