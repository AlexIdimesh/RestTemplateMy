package org.example.repository.impl;

import org.example.db.DBConnection;
import org.example.model.EventsTagEntity;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class EventsTagEntityRepositoryImplTest {

    private static final String TEST_DB_USERNAME = "sasha";
    private static final String TEST_DB_PASSWORD = "1234";
    private static final String TEST_DB_NAME = "eventsHappy";
    private static final String TEST_MIGRATION = "db-migration.sql";

    private static EventsTagEntityRepositoryImpl repository;

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
        properties.setProperty("db.name", TEST_DB_USERNAME);
        properties.setProperty("db.password", TEST_DB_PASSWORD);
        DBConnection connectionManager = new DBConnection(properties);
        repository = new EventsTagEntityRepositoryImpl(connectionManager, new EventsTagResultSetMapperImpl());
        container.start();
    }

    @AfterAll
    static void alterAll() {
        container.stop();
    }

    @Test
    void testFindById() {
        Long idTest = 25L;

        var eventsTagEntity = repository.findById(idTest);
        assertTrue(eventsTagEntity.isEmpty());
    }

    @Test
    void testFindByIdWhenPresent(){
        EventsTagEntity eventsTagEntity = eventsTagEntityCreate();

        var eventsTest = repository.findById(eventsTagEntity.getId()).orElse(null);

        assertNotNull(eventsTest);
        assertEquals(eventsTest.getId(),eventsTest.getId());
    }

    @Test
    void testDeleteById() {
        Long deleteId = 2L;
        boolean eventsTagEntity = repository.deleteById(deleteId);
        assertTrue(eventsTagEntity);
    }

    @Test
    void testFindAll() throws SQLException, ClassNotFoundException {
        int sizeTest = 3;
        List<EventsTagEntity> entityListTest = repository.findAll();
        assertEquals(entityListTest.size(),sizeTest);
    }

    @Test
    void testSave() {
        EventsTagEntity eventsTagEntity = eventsTagEntityCreate();
        EventsTagEntity eventsTagTest = repository.save(eventsTagEntity);

        assertNotNull(eventsTagTest.getId());
        assertEquals(eventsTagTest,eventsTagTest);

    }

    @Test
    void upDated() {
        EventsTagEntity eventsTagEntity = eventsTagEntityCreate();
        EventsTagEntity eventsTagTest = repository.upDated(eventsTagEntity);

        assertNotNull(eventsTagTest.getId());
        assertEquals(eventsTagEntity.getId(),eventsTagTest.getId());
        assertEquals(eventsTagEntity.getTagAuthor(),eventsTagTest.getTagAuthor());
        assertEquals(eventsTagEntity.getTagName(),eventsTagTest.getTagName());
    }

    private static EventsTagEntity eventsTagEntityCreate() {
        EventsTagEntity eventsTagEntity = new EventsTagEntity();
        eventsTagEntity.setId(1L);
        eventsTagEntity.setTagName("");
        eventsTagEntity.setTagAuthor("");
        return eventsTagEntity;
    }
}