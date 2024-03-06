package org.example.service.impl;

import org.example.model.ParticipantsEntity;
import org.example.repository.impl.ParticipantsEntityRepositoryImpl;
import org.example.servlet.dto.ParticipantsDTO;
import org.example.servlet.mapper.ParticipantsMapperMapstructImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipantsServiceImplTest {
    @Mock
    private ParticipantsEntityRepositoryImpl repository = mock(ParticipantsEntityRepositoryImpl.class);
    @Mock
    private ParticipantsMapperMapstructImpl mapstruct = mock(ParticipantsMapperMapstructImpl.class);

    private ParticipantsEntity participants;

    private ParticipantsDTO dto;

    private ParticipantsServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ParticipantsServiceImpl(repository,mapstruct);
        participants = new ParticipantsEntity();
        dto = new ParticipantsDTO();
    }

    @Test
    void testSave() {
        when(mapstruct.toEntity(dto)).thenReturn(participants);
        when(repository.save(participants)).thenReturn(participants);
        when(mapstruct.toDto(participants)).thenReturn(dto);

        ParticipantsDTO participantsDTOTest = service.save(dto);

        assertNotNull(participantsDTOTest);
        assertEquals(dto,participantsDTOTest);

        verify(mapstruct,times(1)).toDto(participants);
        verify(mapstruct,times(1)).toEntity(dto);
        verify(repository,times(1)).save(participants);
    }

    @Test
    void testFindById() {
        Long testId = 1L;
        when(repository.findById(testId)).thenReturn(Optional.ofNullable(participants));
        when(mapstruct.toDto(participants)).thenReturn(dto);

        ParticipantsDTO participantsDTOTest = service.findById(testId);

        assertNotNull(participantsDTOTest);
        assertEquals(dto,participantsDTOTest);

        verify(repository, times(1)).findById(testId);
        verify(mapstruct, times(1)).toDto(participants);
    }

    @Test
    void testFindByIdEvents() {
        Long testId = 1L;

        ParticipantsEntity participantsEntity1 = spy(new ParticipantsEntity());
        ParticipantsEntity participantsEntity2 = spy(new ParticipantsEntity());

        ParticipantsDTO participantsDTO1 = spy(new ParticipantsDTO());
        ParticipantsDTO participantsDTO2 = spy(new ParticipantsDTO());

        List<ParticipantsEntity> participantsEntityList = List.of(participantsEntity1,participantsEntity2);

        when(repository.findByIdEvent(testId)).thenReturn(participantsEntityList);
        when(mapstruct.toDto(participantsEntity1)).thenReturn(participantsDTO1);
        when(mapstruct.toDto(participantsEntity2)).thenReturn(participantsDTO2);

        List<ParticipantsDTO> participantsDTOTest = service.findByIdEvents(testId);


        verify(repository,times(1)).findByIdEvent(testId);
        verify(mapstruct,times(2)).toDto(any(ParticipantsEntity.class));
        assertNotNull(participantsDTOTest);
        assertEquals(2, participantsDTOTest.size());
    }

    @Test
    void testFindAll() throws SQLException, ClassNotFoundException {
        ParticipantsEntity participantsEntity1 = spy(new ParticipantsEntity());
        ParticipantsEntity participantsEntity2 = spy(new ParticipantsEntity());

        ParticipantsDTO participantsDTO1 = spy(new ParticipantsDTO());
        ParticipantsDTO participantsDTO2 = spy(new ParticipantsDTO());

        List<ParticipantsEntity> participantsEntityList = List.of(participantsEntity1,participantsEntity2);

        when(repository.findAll()).thenReturn(participantsEntityList);
        when(mapstruct.toDto(participantsEntity1)).thenReturn(participantsDTO1);
        when(mapstruct.toDto(participantsEntity2)).thenReturn(participantsDTO2);

        List<ParticipantsDTO> participantsDTOTest = service.findAll();


        verify(repository,times(1)).findAll();
        verify(mapstruct,times(2)).toDto(any(ParticipantsEntity.class));
        assertNotNull(participantsDTOTest);
        assertEquals(2, participantsDTOTest.size());

    }

    @Test
    void testDeleteById() {
        Long deletedId = 1L;

        when(repository.deleteById(deletedId)).thenReturn(true);

        boolean deletedServiceIdTest = service.deleteById(deletedId);

        assertTrue(deletedServiceIdTest);
        verify(repository, times(1)).deleteById(deletedId);
    }

    @Test
    void testUpDated() {
        when(mapstruct.toEntity(dto)).thenReturn(participants);
        when(repository.upDated(participants)).thenReturn(participants);
        when(mapstruct.toDto(participants)).thenReturn(dto);

        ParticipantsDTO dtoTest = service.upDated(dto);

        assertEquals(dto,dtoTest);

        verify(repository, times(1)).upDated(participants);
        verify(mapstruct, times(1)).toDto(participants);
        verify(mapstruct, times(1)).toEntity(dto);
    }
}