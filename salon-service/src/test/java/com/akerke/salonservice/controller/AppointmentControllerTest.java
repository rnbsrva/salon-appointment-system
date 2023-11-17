package com.akerke.salonservice.controller;

import com.akerke.salonservice.common.exception.InvalidRequestPayloadException;
import com.akerke.salonservice.common.constants.Gender;
import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.dto.AppointmentDTO;
import com.akerke.salonservice.domain.entity.*;
import com.akerke.salonservice.domain.repository.*;
import com.akerke.salonservice.domain.service.AppointmentService;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapperRepository;
import com.akerke.salonservice.tc.SalonServicePostgresTestContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    private Appointment appointment;
    private Long appointmentId;


    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointmentId = 1L;
        appointment.setId(appointmentId);
    }

    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(put("/appointment/1")
                        .contentType(APPLICATION_JSON)
                        .content("{\"treatmentId\":1,\"userId\":1,\"masterId\":1,\"workTimeId\":1,\"note\":\"Test note\"}"))
                .andExpect(status().isAccepted());
    }

    @Test
    void save_WhenValidDTO_thenReturnValidResponse()
            throws Exception {

        var appointmentDTO = new AppointmentDTO(1L,1L,1L,1L,"note");
        var response = mockMvc.perform(
                        post("/appointment")
                                .accept(ALL_VALUE)
                                .contentType(APPLICATION_JSON_VALUE)
                        .content("{\"treatmentId\":1,\"userId\":1,\"masterId\":1,\"workTimeId\":1,\"note\":\"note\"}")
        );

        response.andExpect(status().isCreated());

//        var responseContent = response.andReturn().getResponse().getContentAsString();
//        var appointment = objectMapper.readValue(responseContent, Appointment.class);
//
//        assertNotNull(appointment.getId());
//        assertNull(appointment.getCreatedTime());
//
//        assertEquals(appointmentDTO.masterId(), appointment.getMasterId());
//        assertEquals(appointmentDTO.note(), appointment.getNote());
//        assertEquals(appointmentDTO.treatmentId(), appointment.getTreatmentId());
//        assertEquals(appointmentDTO.workTimeId(), appointment.getWorkTime().getWorkDayId());
//        assertEquals(appointmentDTO.userId(), appointment.getUserId());

    }

//    @Test
//    void save_whenInvalidDTO_thenReturnProblem() {
//        var appointmentDTO = new AppointmentDTO(
//                null,
//                1L,
//                1L, 1L,
//                "Esil"
//        );
//
//        assertThrows(InvalidRequestPayloadException.class, () -> mockMvc.perform(
//                post("/appointment")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(appointmentDTO))
//        ));
//
//    }


}
