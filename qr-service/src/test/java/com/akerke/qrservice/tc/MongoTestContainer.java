package com.akerke.qrservice.tc;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MongoTestContainer {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0").withExposedPorts(27017)
            .withEnv("MONGO_INITDB_DATABASE", "local")
            .withEnv("MONGO_INIT_ROOT_USERNAME", "minioadmin")
            .withEnv("MONGO_INIT_ROOT_PASSWORD", "minioadmin");

    static {
        mongoDBContainer.start();
    }

}