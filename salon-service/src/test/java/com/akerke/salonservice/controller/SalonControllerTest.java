package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.entity.Address;
import com.akerke.salonservice.domain.entity.Salon;
import com.akerke.salonservice.domain.repository.AddressRepository;
import com.akerke.salonservice.domain.repository.SalonRepository;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapperRepository;
import com.akerke.salonservice.infrastructure.elastic.service.SalonElasticService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalonController.class)
public class SalonControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SalonRepository salonRepository;

    @MockBean
    private SalonService salonService;
    @MockBean
    private SalonElasticService salonElasticService;

    private Salon salon;
    private Long salonId;

    @BeforeEach
    void setUp() {
        salonRepository.deleteAll();

        salon = new Salon();

        salonId = 1L;
        salon.setId(salonId);

    }

    @Test
    void save_whenValidDTO_thenReturnValidResponse()
            throws Exception {
        var salonDto = new SalonDTO(
                1L, "name", "phone","akerke@gmail.com", new AddressDTO(1L, "sd", "dc", "cv"), "test_description"
        );

        var response = mvc.perform(
                post("/salon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salonDto))
        );

        response.andExpect(status().isCreated());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        var responseContent = response.andReturn().getResponse().getContentAsString();
//        var savedSalon = objectMapper.readValue(responseContent, Salon.class);
//
//        assertNotNull(savedSalon.getId());
//        assertNull(savedSalon.getCreatedTime());

    }

    @Test
    void testUpdate() throws Exception {
        mvc.perform(put("/salon/1")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Test note\",\"phone\":1,\"email\":\"akerke@gmail.com\",\"description\":1}"))
                .andExpect(status().isAccepted());
    }


    @Test
    void getById_whenValidId_thenReturnSalon()
            throws Exception {
        salonRepository.save(salon);

        var getByIdResponse = mvc.perform(
                get("/salon/" + salon.getId())
        );

        getByIdResponse
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        var getByIdResponseContent = getByIdResponse.andReturn().getResponse().getContentAsString();
        var salonFromResponse = objectMapper.readValue(getByIdResponseContent, Salon.class);

        assertEquals(salon.getId(), salonFromResponse.getId());
    }


}
