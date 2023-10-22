package com.akerke.qrservice.controller;

import com.akerke.qrservice.repository.QRRepository;
import com.akerke.qrservice.service.QRService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class QRControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private QRService qrService;
    @Autowired
    private QRRepository qrRepository;


    @BeforeEach
    void beforeEach() {
        qrRepository.deleteAll();
    }

    @Test
    public void testGenerateQR() throws Exception {
        String data = "test-data";
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);

        when(qrService.generateQRAsync(any(), eq(data))).thenReturn(future);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/generate")
                        .param("data", data)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        verify(qrService).generateQRAsync(response, data);
    }


}
