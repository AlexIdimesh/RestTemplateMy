package org.example.service.impl;

import org.example.model.EventsTagEntity;
import org.example.repository.impl.EventsTagEntityRepositoryImpl;
import org.example.servlet.dto.EventsTagDTO;
import org.example.servlet.mapper.EventsTagMapperMapstruct;
import org.example.servlet.mapper.EventsTagMapperMapstructImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventsTagServiceImplTest {

    @Mock
    private EventsTagEntityRepositoryImpl repository = mock(EventsTagEntityRepositoryImpl.class);
    @Mock
    private EventsTagMapperMapstruct mapstruct = mock(EventsTagMapperMapstructImpl.class);

    private EventsTagServiceImpl service;
    private EventsTagDTO dto;
    private EventsTagEntity eventsTag;


    @BeforeEach
    void setUp() {
        service = new EventsTagServiceImpl(repository,mapstruct);
        dto = new EventsTagDTO();
        eventsTag = new EventsTagEntity();

    }

    @Test
    void testSave() {
        when(mapstruct.toEntity(dto)).thenReturn(eventsTag);
        when(repository.save(eventsTag)).thenReturn(eventsTag);
        when(mapstruct.toDto(eventsTag)).thenReturn(dto);

        EventsTagDTO eventsTagDTOTest = service.save(dto);

        assertEquals(dto,eventsTagDTOTest);

        verify(mapstruct,times(1)).toDto(eventsTag);
        verify(mapstruct,times(1)).toEntity(dto);
        verify(repository,times(1)).save(eventsTag);

    }

    @Test
    void testFindById() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.ofNullable(eventsTag));
        when(mapstruct.toDto(eventsTag)).thenReturn(dto);

        EventsTagDTO eventsTagDTOTest = service.findById(id);

        assertNotNull(eventsTagDTOTest);
        assertEquals(dto,eventsTagDTOTest);

        verify(repository, times(1)).findById(id);
        verify(mapstruct,times(1)).toDto(eventsTag);

    }

    @Test
    void testFindAll() throws SQLException, ClassNotFoundException {
        EventsTagEntity eventsTag1 = spy(EventsTagEntity.class);
        EventsTagEntity eventsTag2 = spy(EventsTagEntity.class);

        EventsTagDTO eventsTagDTO1 = spy(EventsTagDTO.class);
        EventsTagDTO eventsTagDTO2 = spy(EventsTagDTO.class);

        List<EventsTagEntity> entityList = List.of(eventsTag1,eventsTag2);

        when(repository.findAll()).thenReturn(entityList);
        when(mapstruct.toDto(eventsTag1)).thenReturn(eventsTagDTO1);
        when(mapstruct.toDto(eventsTag2)).thenReturn(eventsTagDTO2);

        List<EventsTagDTO> eventsTagDTOTest = service.findAll();

        verify(repository, times(1)).findAll();
        verify(mapstruct, times(2)).toDto(any(EventsTagEntity.class));

        assertNotNull(eventsTagDTOTest);
        assertEquals(2,eventsTagDTOTest.size());
    }

    @Test
    void testDeleteById() {
        Long deletedId = 1L;

        when(repository.deleteById(deletedId)).thenReturn(true);

        boolean eventsTagDTOTest = service.deleteById(deletedId);

        assertTrue(eventsTagDTOTest);

        verify(repository,times(1)).deleteById(deletedId);
    }

    @Test
    void testUpDated() {
        when(mapstruct.toEntity(dto)).thenReturn(eventsTag);
        when(repository.upDated(eventsTag)).thenReturn(eventsTag);
        when(mapstruct.toDto(eventsTag)).thenReturn(dto);

        EventsTagDTO eventsTagDTOTest = service.upDated(dto);

        assertEquals(dto,eventsTagDTOTest);

        verify(repository, times(1)).upDated(eventsTag);
        verify(mapstruct, times(1)).toDto(eventsTag);
        verify(mapstruct, times(1)).toEntity(dto);
    }
}