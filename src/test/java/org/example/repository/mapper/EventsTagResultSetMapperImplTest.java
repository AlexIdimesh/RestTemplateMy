package org.example.repository.mapper;

import org.example.model.EventsTagEntity;
import org.example.repository.mapper.eventstags.EventsTagResultSetMapperImpl;
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
class EventsTagResultSetMapperImplTest {

    private EventsTagResultSetMapperImpl mapper;
    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        mapper = new EventsTagResultSetMapperImpl();
    }

    @Test
    void testEventTagMap() throws SQLException {
        when(resultSet.getLong("event_tag_id")).thenReturn(1L);
        when(resultSet.getString("event_tag_name")).thenReturn("test");
        when(resultSet.getString("event_tag_author")).thenReturn("testCity");

        EventsTagEntity eventsTagEntity = mapper.map(resultSet);

        assertAll(
                () -> assertEquals(1L, eventsTagEntity.getId()),
                () -> assertEquals("test", eventsTagEntity.getTagName()),
                () -> assertEquals("testCity", eventsTagEntity.getTagAuthor())
        );

    }
}