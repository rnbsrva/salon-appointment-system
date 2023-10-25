package com.akerke.qrservice.feign;

import com.akerke.qrservice.utils.ByteArrayMultipartFile;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = StorageServiceFeignClient.class)
public class StorageServiceFeignClientTest {

    @Autowired
    private StorageServiceFeignClient feignClient;

    @MockBean
    private StorageServiceFeignClient mockFeignClient;

    @Test
    void testSaveToStorage() {
        String name = "test-file";
        ByteArrayMultipartFile file = new ByteArrayMultipartFile("Hello, World!".getBytes(), "file", "test-file.txt", "text/plain");

        doNothing().when(mockFeignClient).saveToStorage(file, name);

        feignClient.saveToStorage(file, name);

        verify(mockFeignClient, times(1)).saveToStorage(file, name);
    }
}
