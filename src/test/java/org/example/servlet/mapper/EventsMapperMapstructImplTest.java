package org.example.servlet.mapper;

import org.example.model.EventsEntity;
import org.example.servlet.dto.EventDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class EventsMapperMapstructImplTest {

    private EventsMapperMapstructImpl mapstruct = mock(EventsMapperMapstructImpl.class);

    @Test
    void testToDTO() {
        EventsEntity eventsEntity = mock(EventsEntity.class);
        EventDTO expectedDTO = mock(EventDTO.class);

        when(mapstruct.toDTO(any(EventsEntity.class))).thenReturn(expectedDTO);

        EventDTO actualDTO = mapstruct.toDTO(eventsEntity);

        assertEquals(expectedDTO.getId(), actualDTO.getId());
        assertEquals(expectedDTO.getName(), actualDTO.getName());
        assertEquals(expectedDTO.getCity(), actualDTO.getCity());
    }

    @Test
    void testToEntity() {
        EventDTO eventDTO = mock(EventDTO.class);

        EventsEntity expectedEntity = mock(EventsEntity.class);

        when(mapstruct.toEntity(any(EventDTO.class))).thenReturn(expectedEntity);

        EventsEntity actualEntity = mapstruct.toEntity(eventDTO);

        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
        assertEquals(expectedEntity.getCity(), actualEntity.getCity());
    }
}