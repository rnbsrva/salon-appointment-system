package com.akerke.storageservice.controller;

import com.akerke.storageservice.domain.service.impl.MinIOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@WebMvcTest
@AutoConfigureMockMvc
public class MinIOControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MinIOServiceImpl minIOService;

//    @Test
//    void test_uploadFileToMinIO() throws Exception {
//        Long testTarget = 85L;
//        AttachmentSource source = AttachmentSource.valueOf("MASTER_IMAGE");
//        String name = "filename";
//
//        MultipartFile file = new MockMultipartFile(name, name.getBytes());
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/upload")
//                        .param("name", name)
//                        .param("source", String.valueOf(source))
//                        .param("target", String.valueOf(testTarget))
//        );
//
//        var dto = new FileOperationDTO(testTarget, source, name);
//
//        doNothing().when(minIOService).putObject(dto, file);
//
//        verify(minIOService, times(1)).putObject(dto, file);
//
//    }

}
