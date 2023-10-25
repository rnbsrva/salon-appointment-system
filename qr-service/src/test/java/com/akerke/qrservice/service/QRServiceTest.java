package com.akerke.qrservice.service;

import com.akerke.qrservice.entity.QR;
import com.akerke.qrservice.feign.StorageServiceFeignClient;
import com.akerke.qrservice.repository.QRRepository;
import com.akerke.qrservice.service.impl.QRServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class QRServiceTest {

    @Mock
    private StorageServiceFeignClient storageServiceFeignClient;
    @Mock
    private QRRepository qrRepository;
    @InjectMocks
    private QRServiceImpl qrService;


    @Test
    public void testGenerateQRAsync_QRExists() throws Exception {
        String link = "existingLink";
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(qrRepository.findByLink(link)).thenReturn(Optional.of(new QR(link)));

        CompletableFuture<Void> result = qrService.generateQRAsync(response, link);

        // Ensure that response.sendRedirect is called
        result.get();
        verify(response, times(1)).sendRedirect(anyString());
    }


    @Test
    public void testGenerateQRAsync_QRDoesNotExist() throws Exception {
        String link = "nonExistingLink";
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(qrRepository.findByLink(link)).thenReturn(Optional.empty());
        when(qrRepository.save(any(QR.class))).thenReturn(new QR(link));

        CompletableFuture<Void> result = qrService.generateQRAsync(response, link);

        // Ensure that feignClient.saveToStorage is called
        result.get();
        verify(storageServiceFeignClient, times(1)).saveToStorage(any(), eq(link));
    }

    @Test
    public void testGenerateMultipartQR() throws Exception {
        String data = "testData";
        MockHttpServletResponse response = new MockHttpServletResponse();
        CompletableFuture<MultipartFile> result = qrService.generateMultipartQR(response, data);

        // Ensure that a valid MultipartFile is returned
        MultipartFile multipartFile = result.get();
        assertEquals(data, multipartFile.getOriginalFilename());
    }

}