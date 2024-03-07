package org.example.repository.impl;

import org.example.db.DBConnection;
import org.example.model.CombinedEntity;
import org.example.model.EventsEntity;
import org.example.repository.mapper.events.EventsResultSetMapperImpl;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
class EventsEntityRepositoryImplTest {
    private static final String TEST_DB_USERNAME = "sasha";
    private static final String TEST_DB_PASSWORD = "1234";
    private static final String TEST_DB_NAME = "eventsHappy";
    private static final String TEST_MIGRATION = "db-migration.sql";

    private static EventsEntityRepositoryImpl repository;
    @Container
    public static final MySQLContainer<?> container =
            new MySQLContainer<>("mysql:latest")
                    .withDatabaseName(TEST_DB_NAME)
                    .withUsername(TEST_DB_USERNAME)
                    .withPassword(TEST_DB_PASSWORD)
                    .withInitScript(TEST_MIGRATION);

    @BeforeAll
    static void beforeAll() {
        Properties properties = new Properties();
        properties.setProperty("db.url", container.getJdbcUrl());
        properties.setProperty("db.username", TEST_DB_USERNAME);
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
    void testFindByIdTest() {
        Long id = 25L;
        var eventsEntity = repository.findById(id);

        assertTrue(eventsEntity.isEmpty());
    }

    @Test
    void testFindByIdWhenPresent() {
        EventsEntity eventsEntity = eventsEntityCreated();

        var eventsTest = repository.findById(eventsEntity.getId()).orElse(null);

        assertNotNull(eventsTest);
        assertEquals(eventsEntity.getId(), eventsTest.getId());
    }

    @Test
    void testDeleteById() {
        Long deleteId = 2L;
        boolean eventsEntity = repository.deleteById(deleteId);
        assertTrue(eventsEntity);
    }

    @Test
    void testFindAll() {
        int expectedSize = 3;
        List<EventsEntity> all = repository.findAll();
        Assertions.assertEquals(expectedSize, all.size());
    }

    @Test
    void testSave() {
        EventsEntity eventsEntity = eventsEntityCreated();
        EventsEntity saveEventEntity = repository.save(eventsEntity);

        assertNotNull(saveEventEntity.getId());
        assertEquals(eventsEntity, saveEventEntity);
    }

    @Test
    void testFindAllEventsByEventTag() {
        Long eventId = 1L;
        List<CombinedEntity> entities = repository.findAllEventsByEventTag(eventId);

        assertNotNull(entities);
    }
    @Test
    void testUpDate() {
        EventsEntity eventsEntity = eventsEntityCreated();
        EventsEntity eventsTest = repository.upDated(eventsEntity);

        assertNotNull(eventsTest.getId());
        assertEquals(eventsEntity.getId(),eventsTest.getId());
        assertEquals(eventsEntity.getCity(),eventsTest.getCity());
        assertEquals(eventsEntity.getName(),eventsTest.getName());

    }
    private static EventsEntity eventsEntityCreated() {
        EventsEntity eventsEntity = new EventsEntity();
        eventsEntity.setId(1L);
        eventsEntity.setName("");
        eventsEntity.setCity("");
        return eventsEntity;
    }
}