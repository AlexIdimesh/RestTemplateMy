package org.example.service.impl;

import org.example.model.CombinedEntity;
import org.example.model.EventsEntity;
import org.example.repository.impl.EventsEntityRepositoryImpl;
import org.example.servlet.dto.CombinedEntityDTO;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.mapper.CombinedEntityMapstruct;
import org.example.servlet.mapper.EventsMapperMapstructImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventsServiceImplTest {
    @Mock
    private static EventsEntityRepositoryImpl repository = mock(EventsEntityRepositoryImpl.class);
    @Mock
    private static EventsMapperMapstructImpl mapperMapstruct = mock(EventsMapperMapstructImpl.class);
    @Mock
    private static CombinedEntityMapstruct mapstruct = mock(CombinedEntityMapstruct.class);

    private EventsServiceImpl eventsService;

    private EventsEntity events;

    private EventDTO dto;

    @BeforeEach
    void setUp() {
       eventsService = new EventsServiceImpl(repository,mapperMapstruct,mapstruct);
       events = new EventsEntity();
       dto = new EventDTO();
    }

    @Test
    void testSave() {
        when(mapperMapstruct.toEntity(dto)).thenReturn(events);
        when(repository.save(events)).thenReturn(events);
        when(mapperMapstruct.toDTO(events)).thenReturn(dto);

        EventDTO saveEventsDTO = eventsService.save(dto);

        assertEquals(dto, saveEventsDTO);

        verify(mapperMapstruct, times(1)).toEntity(dto);
        verify(mapperMapstruct, times(1)).toDTO(events);
        verify(repository, times(1)).save(events);
    }
    @Test
    void testFindById() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.ofNullable(events));
        when(mapperMapstruct.toDTO(events)).thenReturn(dto);

        EventDTO foundEventDTO = eventsService.findById(id);

        assertNotNull(foundEventDTO);
        assertEquals(dto,foundEventDTO);

        verify(repository, times(1)).findById(id);
        verify(mapperMapstruct, times(1)).toDTO(events);
    }
    @Test
    void testFindEventTagsByEventId() {
        Long eventId = 1L;

        List<CombinedEntity> entityList = new ArrayList<>();
        entityList.add(mock(CombinedEntity.class));
        entityList.add(mock(CombinedEntity.class));

        List<CombinedEntityDTO> entityDTOList = new ArrayList<>();
        entityDTOList.add(mock(CombinedEntityDTO.class));
        entityDTOList.add(mock(CombinedEntityDTO.class));

        when(repository.findAllEventsByEventTag(eventId)).thenReturn(entityList);
        when(mapstruct.toDTO(any())).thenReturn(mock(CombinedEntityDTO.class));

        List<CombinedEntityDTO> foundEventDTOs = eventsService.findEventTagsByEventId(eventId);

        assertNotNull(foundEventDTOs);
        assertEquals(entityDTOList.size(), foundEventDTOs.size());

        verify(repository, times(1)).findAllEventsByEventTag(eventId);
        verify(mapstruct, times(entityList.size())).toDTO(any());
    }
    @Test
    void testFindAll() throws SQLException, ClassNotFoundException {
        EventsEntity eventsEntity = spy(new EventsEntity());
        EventsEntity eventsEntity1 = spy(new EventsEntity());

        EventDTO eventDTO = spy(new EventDTO());
        EventDTO eventDTO1 = spy(new EventDTO());

        List<EventsEntity> eventsEntityList = List.of(eventsEntity,eventsEntity1);

        when(repository.findAll()).thenReturn(eventsEntityList);
        when(mapperMapstruct.toDTO(eventsEntity)).thenReturn(eventDTO);
        when(mapperMapstruct.toDTO(eventsEntity1)).thenReturn(eventDTO1);


        List<EventDTO> foundEventDTOs = eventsService.findAll();

        verify(repository, times(1)).findAll();
        verify(mapperMapstruct, times(2)).toDTO(any(EventsEntity.class));

        assertNotNull(foundEventDTOs);
        Assert.assertEquals(2, foundEventDTOs.size());
    }

    @Test
    void testDeleteById() {
        Long deleteId = 1L;

        when(repository.deleteById(deleteId)).thenReturn(true);
        boolean deleteThisId = eventsService.deleteById(deleteId);

        assertTrue(deleteThisId);
        verify(repository, times(1)).deleteById(deleteId);

    }

    @Test
    void testUpDated() {
        when(mapperMapstruct.toEntity(dto)).thenReturn(events);
        when(repository.upDated(events)).thenReturn(events);
        when(mapperMapstruct.toDTO(events)).thenReturn(dto);

        EventDTO saveEventsDTO = eventsService.upDated(dto);

        assertEquals(dto, saveEventsDTO);

        verify(mapperMapstruct, times(1)).toEntity(dto);
        verify(mapperMapstruct, times(1)).toDTO(events);
        verify(repository, times(1)).upDated(events);
    }
}