package org.example.repository.mapper;

import org.example.model.ParticipantsEntity;
import org.example.repository.mapper.partipants.ParticipantsResultSetMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantsResultSetMapperImplTest {
    private ParticipantsResultSetMapperImpl mapper;
    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        mapper = new ParticipantsResultSetMapperImpl();
    }

    @Test
    void map() throws SQLException {
        when(resultSet.getLong("participants_id")).thenReturn(1L);
        when(resultSet.getString("participants_name")).thenReturn("test");
        when(resultSet.getString("participants_number")).thenReturn("testCity");

        ParticipantsEntity participants = mapper.map(resultSet);

        assertAll(
                () -> assertEquals(1L, participants.getId()),
                () -> assertEquals("test", participants.getName()),
                () -> assertEquals("testCity", participants.getNumber())
        );
    }
}