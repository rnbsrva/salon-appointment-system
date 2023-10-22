package com.akerke.qrservice.repository;

import com.akerke.qrservice.entity.QR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class QRRepositoryTest {

    @Autowired
    private QRRepository qrRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    static MongoDBContainer mongoContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoContainer::getReplicaSetUrl);
    }

    @BeforeEach
    public void setUp() {
        mongoContainer.start();
        String containerIpAddress = mongoContainer.getHost();
        Integer containerPort = mongoContainer.getMappedPort(27017);


    }

    @Test
    public void findByLink_thenLinkExisting() {
        var qr = new QR("example-link");
        qrRepository.save(qr);

        var foundQR = qrRepository.findByLink("example-link");

        assertTrue(foundQR.isPresent());
        assertEquals("example-link", foundQR.get().getLink());
    }

    @Test
    public void findByLink_thenLinkNonExisting() {
        var foundQR = qrRepository.findByLink("non-existent-link");

        assertThat(foundQR).isEmpty();
    }
}
