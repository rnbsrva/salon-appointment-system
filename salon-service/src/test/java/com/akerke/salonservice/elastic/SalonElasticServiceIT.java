package com.akerke.salonservice.elastic;

import com.akerke.salonservice.common.payload.SalonSearchRequest;
import com.akerke.salonservice.domain.entity.Address;
import com.akerke.salonservice.generator.SalonGenerator;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapper;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapperRepository;
import com.akerke.salonservice.infrastructure.elastic.service.SalonElasticService;
import com.akerke.salonservice.tc.SalonServiceElasticsearchContainer;
import com.akerke.salonservice.tc.SalonServicePostgresTestContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalonElasticServiceIT extends SalonServicePostgresTestContainer {

    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new SalonServiceElasticsearchContainer();

    @Autowired
    private ElasticsearchTemplate elastic;

    @Autowired
    private SalonElasticService salonElasticService;

    @Autowired
    private SalonWrapperRepository salonWrapperRepository;

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @BeforeEach
    void testIsContainerRunning() {
        assertTrue(elasticsearchContainer.isRunning());
        recreateIndex();
        salonWrapperRepository.saveAll(
                SalonGenerator.salonWrappers()
        );
    }

    private void recreateIndex() {
        if (elastic.indexOps(SalonWrapper.class).exists()) {
            elastic.indexOps(SalonWrapper.class).delete();
            elastic.indexOps(SalonWrapper.class).create();
        }
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

    @Test
    void testSave() {
        SalonSearchRequest searchRequest = new SalonSearchRequest(
                "Salon A", null, null
        );

        salonWrapperRepository.findAll()
                        .forEach(System.out::println);

        System.out.println(salonElasticService.search(searchRequest, 0, 5));
    }

}
