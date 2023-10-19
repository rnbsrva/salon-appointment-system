package com.akerke.salonservice.mapper;

import com.akerke.salonservice.constants.WeekDay;
import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.entity.WorkDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class WorkDayMapperTest {

    private WorkDayMapper workDayMapper;
    private WorkDayDTO workDayDTO;
    private WorkDay workDay;


    @BeforeEach
    public void setUp() {
        workDayMapper = Mappers.getMapper(WorkDayMapper.class);
        workDayDTO = createWorkDayDTO();
        workDay = createWorkDay();
    }

    @Test
    public void toModel_whenWorkDayDTOProvided_thenReturnMappedWorkDay() {

        var workDay = workDayMapper.toModel(workDayDTO);

        assertNotNull(workDay);
        assertEquals(workDayDTO.weekDay(), workDay.getWeekDay());
        assertEquals(workDayDTO.workStartTime(), workDay.getWorkStartTime());
        assertEquals(workDayDTO.workEndTime(), workDay.getWorkEndTime());
        assertEquals(workDayDTO.isHoliday(), workDay.getIsHoliday());
        assertTrue(workDay.getWorkTimes().isEmpty());
    }

    @Test
    public void toDTO_whenWorkDayProvided_thenReturnMappedWorkDayDTO() {
        var workDayDTO = workDayMapper.toDTO(workDay);

        assertNotNull(workDayDTO);
        assertEquals(workDay.getWeekDay(), workDayDTO.weekDay());
        assertEquals(workDay.getWorkStartTime(), workDayDTO.workStartTime());
        assertEquals(workDay.getWorkEndTime(), workDayDTO.workEndTime());
        assertEquals(workDay.getIsHoliday(), workDayDTO.isHoliday());
    }

    @Test
    public void toDTO_whenWorkDayWithNonNullPropertiesProvided_thenReturnWorkDayDTOWithNonNullProperties() {
        var workDayDTO = workDayMapper.toDTO(workDay);

        assertNotNull(workDayDTO);
        assertNotNull(workDayDTO.weekDay());
        assertNotNull(workDayDTO.workStartTime());
        assertNotNull(workDayDTO.workEndTime());
        assertNotNull(workDayDTO.isHoliday());
    }

    private WorkDayDTO createWorkDayDTO() {
        return new WorkDayDTO(1L, 1L, WeekDay.MONDAY, new Date(), new Date(), false);
    }

    private WorkDay createWorkDay() {
        WorkDay result = new WorkDay();
        result.setWeekDay(WeekDay.TUESDAY);
        result.setWorkStartTime(new Date());
        result.setWorkEndTime(new Date());
        result.setIsHoliday(false);

        var salon = new Salon();
        salon.setId(1L);
        result.setSalon(salon);

        var master = new Master();
        master.setId(1L);
        result.setMaster(master);

        return result;
    }
}