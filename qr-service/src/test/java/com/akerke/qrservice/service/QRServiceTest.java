package com.akerke.qrservice.service;

import com.akerke.qrservice.entity.QR;
import com.akerke.qrservice.feign.StorageServiceFeignClientTest;
import com.akerke.qrservice.repository.QRRepository;
import com.akerke.qrservice.service.impl.QRServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QRServiceTest {

    @Mock
    private QRRepository qrRepository;

    @Mock
    private StorageServiceFeignClientTest feignClient;

    @InjectMocks
    private QRServiceImpl qrService;

    private String link = "testLink";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGenerateQRAsyncWhenQRNotFoundThenSaveQRToRepository() throws ExecutionException, InterruptedException {
        when(qrRepository.findByLink(link)).thenReturn(Optional.empty());

        qrService.generateQRAsync(new MockHttpServletResponse(), link).get();

        verify(qrRepository, times(1)).findByLink(link);
//        verify(feignClient, never()).saveToStorage(any(MultipartFile.class), anyString());
    }

    @Test
    public void testGenerateQRAsyncWhenQRFoundThenRedirectToStorageService() throws ExecutionException, InterruptedException {
        when(qrRepository.findByLink(link)).thenReturn(Optional.of(new QR(link)));

        qrService.generateQRAsync(new MockHttpServletResponse(), link).get();

        verify(qrRepository, times(1)).findByLink(link);
//        verify(feignClient, never()).saveToStorage(any(MultipartFile.class), anyString());
    }

    @Test
    public void testGenerateMultipartQRWhenQRFoundThenGenerateQR() throws ExecutionException, InterruptedException {
        when(qrRepository.findByLink(link)).thenReturn(Optional.of(new QR("new link")));

        MultipartFile multipartFile = qrService.generateMultipartQR(new MockHttpServletResponse(), link).get();

        assertNotNull(multipartFile);
        assertEquals(link, multipartFile.getName());
        assertEquals(link, multipartFile.getOriginalFilename());
        assertEquals("multipart/form-data", multipartFile.getContentType());

        verify(qrRepository, times(1)).findByLink(link);
//        verify(feignClient, never()).saveToStorage(any(MultipartFile.class), anyString());
    }

}
