package org.example.repository.mapper;

import org.example.model.EventsEntity;
import org.example.repository.mapper.events.EventsResultSetMapperImpl;
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
class EventsResultSetMapperImplTest {

    private EventsResultSetMapperImpl mapper;
    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        mapper = new EventsResultSetMapperImpl();
    }

    @Test
    void testEventsMap() throws SQLException {
        when(resultSet.getLong("events_id")).thenReturn(1L);
        when(resultSet.getString("events_name")).thenReturn("test");
        when(resultSet.getString("events_city")).thenReturn("testCity");

        EventsEntity eventsEntity = mapper.map(resultSet);

        assertAll(
                () -> assertEquals(1L, eventsEntity.getId()),
                () -> assertEquals("test", eventsEntity.getName()),
                () -> assertEquals("testCity", eventsEntity.getCity())
        );
    }
}