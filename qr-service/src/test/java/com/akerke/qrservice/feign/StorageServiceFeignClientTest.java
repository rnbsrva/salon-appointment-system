package com.akerke.qrservice.feign;

import com.akerke.qrservice.utils.ByteArrayMultipartFile;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.doNothing;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class StorageServiceFeignClientTest {

    @Mock
    private StorageServiceFeignClient storageServiceFeignClient;

    @Mock
    private ByteArrayMultipartFile multipartFile;


    @Test
    public void testSaveToStorageWhenAllParametersProvidedThenNoException() {
        doNothing().when(storageServiceFeignClient).saveToStorage(multipartFile, "QR_IMAGE", "testName");

        storageServiceFeignClient.saveToStorage(multipartFile, "QR_IMAGE", "testName");

    }

    @Test
    public void testSaveToStorageWhenSourceNotProvidedThenNoException() {
        doNothing().when(storageServiceFeignClient).saveToStorage(multipartFile, "testName");

        storageServiceFeignClient.saveToStorage(multipartFile, "testName");

    }


    @Test
    public void testSaveToStorageWhenNameNotProvidedThenNoException() {
        doNothing().when(storageServiceFeignClient).saveToStorage(multipartFile, "QR_IMAGE", null);

        storageServiceFeignClient.saveToStorage(multipartFile, "QR_IMAGE", null);

    }
}
