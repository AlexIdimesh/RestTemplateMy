package org.example.repository.impl;

import org.example.db.DBConnection;
import org.example.model.ParticipantsEntity;
import org.example.repository.mapper.partipants.ParticipantsResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class ParticipantsEntityRepositoryImplTest {


    private static final String TEST_DB_USERNAME = "sasha";
    private static final String TEST_DB_PASSWORD = "1234";
    private static final String TEST_DB_NAME = "eventsHappy";
    private static final String TEST_MIGRATION = "db-migration.sql";

    private static ParticipantsEntityRepositoryImpl repository;

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
        repository = new ParticipantsEntityRepositoryImpl(connectionManager, new ParticipantsResultSetMapperImpl());
        container.start();
    }

    @AfterAll
    static void alterAll() {
        container.stop();
    }

    @Test
    void testFindByIdWhenPresent() {
        ParticipantsEntity participants = participantsEntityCreate();

        var participantsEntityTest = repository.findById(participants.getId()).orElse(null);

        Assertions.assertNotNull(participantsEntityTest);
        assertEquals(participants.getId(), participantsEntityTest.getId());
    }

    @Test
    void findByIdEvent() {
        Long participantId = 2L;
        List<ParticipantsEntity> participants = repository.findByIdEvent(participantId);

        assertNotNull(participants);
        assertTrue(participants.isEmpty());
    }

    @Test
    void deleteById() {
        Long deleteId = 1L;
        boolean entity = repository.deleteById(deleteId);

        assertTrue(entity);
    }

    @Test
    void findAll() throws SQLException, ClassNotFoundException {
        int sizeTest = 3;
        List<ParticipantsEntity> entityListTest = repository.findAll();
        assertEquals(entityListTest.size(),sizeTest);
    }

    @Test
    void save() {
        ParticipantsEntity participantsEntity = participantsEntityCreate();
        ParticipantsEntity participantsTest = repository.save(participantsEntity);

        assertNotNull(participantsTest);
        assertEquals(participantsEntity,participantsTest);
    }

    @Test
    void upDated() {
        ParticipantsEntity participantsEntity = participantsEntityCreate();
        ParticipantsEntity entityTest = repository.upDated(participantsEntity);

        assertNotNull(entityTest);
        assertEquals(participantsEntity.getId(),entityTest.getId());
        assertEquals(participantsEntity.getName(),entityTest.getName());
        assertEquals(participantsEntity.getNumber(),entityTest.getNumber());
    }

    private static ParticipantsEntity participantsEntityCreate(){
        ParticipantsEntity participants = new ParticipantsEntity();
        participants.setId(1L);
        participants.setName("");
        participants.setNumber("");
        return participants;
    }
}