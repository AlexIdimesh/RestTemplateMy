package org.example.repository.impl;
import org.example.db.ConnectionManager;
import org.example.db.DBConnection;
import org.example.model.EventsEntity;
import org.example.repository.mapper.events.EventsResultSetMapperImpl;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapperImpl;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@Testcontainers
class EventsEntityRepositoryImplTest {
    private static final String TEST_DB_USERNAME = "test";
    private static final String TEST_DB_PASSWORD = "test";
    private static final String TEST_DB_NAME = "eventsHappy";
    private static final String TEST_MIGRATION = "db-migration.sql";

    private static EventsEntityRepositoryImpl repository;
    @Container
    public static final MySQLContainer<?> container =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName(TEST_DB_NAME)
                    .withUsername(TEST_DB_USERNAME)
                    .withPassword(TEST_DB_PASSWORD)
                    .withInitScript(TEST_MIGRATION);

    @BeforeAll
    static void beforeAll() {
       Properties properties = new Properties();
       properties.setProperty("db.url", container.getJdbcUrl());
       properties.setProperty("db.name", TEST_DB_USERNAME);
       properties.setProperty("db.password", TEST_DB_PASSWORD);
       DBConnection connectionManager = new DBConnection(properties);
       repository = new EventsEntityRepositoryImpl(connectionManager, new EventsResultSetMapperImpl(), new EventsTagResultSetMapperImpl());
       container.start();
    }
    @AfterAll
    static void alterAll() {
        container.stop();
    }


    @Test
    void findByIdTest() {
        Long id = 1L;
        EventsEntity eventsEntity = new EventsEntity();
        eventsEntity.setId(id);
        eventsEntity.setName("Вася пупкин");
        eventsEntity.setCity("Питер");

        Optional<EventsEntity> events = Optional.ofNullable(repository.findById(id));
        Assertions.assertEquals(eventsEntity, events.get());
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
    }
}