package com.akerke.qrservice.controller;

import com.akerke.qrservice.service.impl.QRServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

@WebMvcTest
@AutoConfigureMockMvc
public class QRControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QRServiceImpl qrService;

    @Test
    void testGenerateQR() throws Exception {
        String testData = "test-data";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/generate")
                .param("data", testData)
        );

        doReturn(CompletableFuture.completedFuture(null)).when(qrService).generateQRAsync(any(MockHttpServletResponse.class), eq (testData));


        verify(qrService, times(1)).generateQRAsync(any(MockHttpServletResponse.class), eq(testData));
    }

}
