package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.entity.Address;
import com.akerke.salonservice.domain.repository.AddressRepository;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapperRepository;
import com.akerke.salonservice.tc.SalonServicePostgresTestContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {
        SalonServicePostgresTestContainer.Initializer.class,
})
class AddressControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AddressRepository addressRepository;

    @MockBean
    private SalonWrapperRepository salonWrapperRepository;

    private Address address;
    private Long addressId;

    @BeforeEach
    void setUp() {
        address = new Address();
        addressId = 1L;
        address.setId(addressId);

        addressRepository.deleteAll();
    }

    @Test
    void save_whenValidDTO_thenReturnValidResponse()
            throws Exception {
        var addressDTO = new AddressDTO(
                59L,
                "Expo",
                "Astana",
                "Esil"
        );

        var response = mvc.perform(
                post("/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDTO))
        );

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        var responseContent = response.andReturn().getResponse().getContentAsString();
        var address = objectMapper.readValue(responseContent, Address.class);

        assertNotNull(address.getId());
        assertNull(address.getCreatedTime());

        assertEquals(addressDTO.city(), address.getCity());
        assertEquals(addressDTO.state(), address.getState());
        assertEquals(addressDTO.street(), address.getStreet());
        assertEquals(addressDTO.houseNumber(), address.getHouseNumber());
    }

    @Test
    void save_whenInvalidDTO_thenReturnProblem()
            throws Exception {
        var addressDTO = new AddressDTO(
                null,
                "Expo",
                "Astana",
                "Esil"
        );

        var response = mvc.perform(
                post("/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDTO))
        );

        response.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        var responseContent = response.andReturn().getResponse().getContentAsString();
        var problem = objectMapper.readValue(responseContent, ProblemDetail.class);

        assertEquals("Bad Request", problem.getTitle());
        assertEquals(400, problem.getStatus());
    }


    @Test
    void delete_whenInvalidId_thenReturnProblem()
            throws Exception {
        var deleteByIdResponse = mvc.perform(
                delete("/address/" + address.getId())
        );

        deleteByIdResponse
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isNotFound());

        var deleteByIdResponseContent = deleteByIdResponse.andReturn().getResponse().getContentAsString();
        var problem = objectMapper.readValue(deleteByIdResponseContent, ProblemDetail.class);

        assertEquals(problem.getStatus(), 404);
        assertEquals(problem.getDetail(), "Address with id: %d not found".formatted(addressId));
    }

    @Test
    void getById_whenValidId_thenReturnAddress()
            throws Exception {
        addressRepository.save(address);

        var getByIdResponse = mvc.perform(
                get("/address/" + address.getId())
        );

        getByIdResponse
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        var getByIdResponseContent = getByIdResponse.andReturn().getResponse().getContentAsString();
        var addressFromResponse = objectMapper.readValue(getByIdResponseContent, Address.class);

        assertEquals(address.getId(), addressFromResponse.getId());
    }

    @Test
    void getById_whenInvalidId_thenReturnProblem()
    throws Exception{
        var getByIdResponse = mvc.perform(
                get("/address/" + address.getId())
        );

        getByIdResponse
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isNotFound());


        var getByIdResponseContent = getByIdResponse.andReturn().getResponse().getContentAsString();
        var problem = objectMapper.readValue(getByIdResponseContent, ProblemDetail.class);

        assertEquals(problem.getStatus(), 404);
        assertEquals(problem.getDetail(), "Address with id: %d not found".formatted(addressId));
    }

}