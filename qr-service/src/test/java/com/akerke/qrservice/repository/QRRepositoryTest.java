package com.akerke.qrservice.repository;


import com.akerke.qrservice.entity.QR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QRRepositoryTest {

    @Autowired
    private QRRepository qrRepository;

    @AfterEach
    void tearDown(){
        qrRepository.deleteAll();
    }

    @Test
    void testFindByLinkWhenLinkExists() {

        QR qr = new QR("test-link");

        qrRepository.save(qr);

        Optional<QR> optionalQR = qrRepository.findByLink("test-link");

        assertTrue(optionalQR.isPresent());

        assertEquals("test-link", optionalQR.get().getLink());
    }

    @Test
    void testFindByLinkWhenLinkDoesNotExist() {
        Optional<QR> optionalQR = qrRepository.findByLink("non-existing-link");

        assertFalse(optionalQR.isPresent());
    }


}
