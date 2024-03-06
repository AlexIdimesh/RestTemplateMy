package org.example.servlet.mapper;

import org.example.model.EventsTagEntity;
import org.example.servlet.dto.EventsTagDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
class EventsTagMapperMapstructImplTest {

    private EventsTagMapperMapstructImpl mapstruct = mock(EventsTagMapperMapstructImpl.class);

    @Test
    void testToDTO() {
        EventsTagEntity eventsTagEntity = mock(EventsTagEntity.class);
        EventsTagDTO eventsTagDTO = mock(EventsTagDTO.class);

        when(mapstruct.toDto(any(EventsTagEntity.class))).thenReturn(eventsTagDTO);

        EventsTagDTO actualDTO = mapstruct.toDto(eventsTagEntity);

        assertEquals(eventsTagDTO.getId(), actualDTO.getId());
        assertEquals(eventsTagDTO.getTagName(), actualDTO.getTagName());
        assertEquals(eventsTagDTO.getTagAuthor(), actualDTO.getTagAuthor());
    }

    @Test
   void testToEntity() {
        EventsTagDTO eventsTagDTO = mock(EventsTagDTO.class);
        EventsTagEntity eventsTagEntity = mock(EventsTagEntity.class);

        when(mapstruct.toEntity(any(EventsTagDTO.class))).thenReturn(eventsTagEntity);

        EventsTagEntity actualEntity = mapstruct.toEntity(eventsTagDTO);

        assertEquals(eventsTagDTO.getId(), actualEntity.getId());
        assertEquals(eventsTagDTO.getTagName(), actualEntity.getTagName());
        assertEquals(eventsTagDTO.getTagAuthor(), actualEntity.getTagAuthor());
    }
}