package com.example.educationalqualityproject.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class BaseIntegrationTest {

    static {
    System.setProperty("DOCKER_HOST", "npipe:////./pipe/dockerDesktopLinuxEngine");
    System.setProperty("TESTCONTAINERS_RYUK_DISABLED", "true");
    System.setProperty("TESTCONTAINERS_CHECKS_DISABLE", "true");
}

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:7.0")
                    .withEnv("MONGO_INITDB_DATABASE", "testdb");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.data.mongodb.uri",
                mongoDBContainer::getReplicaSetUrl
        );
    }
}