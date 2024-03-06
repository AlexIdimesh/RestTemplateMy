package org.example.servlet.mapper;

import org.example.model.ParticipantsEntity;
import org.example.servlet.dto.ParticipantsDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
class ParticipantsMapperMapstructImplTest {

    private ParticipantsMapperMapstructImpl mapstruct = mock(ParticipantsMapperMapstructImpl.class);

    @Test
    void testToDTO() {
        ParticipantsEntity participants = mock(ParticipantsEntity.class);
        ParticipantsDTO participantsDTO = mock(ParticipantsDTO.class);

        when(mapstruct.toDto(any(ParticipantsEntity.class))).thenReturn(participantsDTO);

        ParticipantsDTO actualDTO = mapstruct.toDto(participants);

        assertEquals(participantsDTO.getId(), actualDTO.getId());
        assertEquals(participantsDTO.getName(), actualDTO.getName());
        assertEquals(participantsDTO.getNumber(), actualDTO.getNumber());
    }

    @Test
    void testToEntity() {
        ParticipantsDTO participantsDTO = mock(ParticipantsDTO.class);
        ParticipantsEntity participants = mock(ParticipantsEntity.class);

        when(mapstruct.toEntity(any(ParticipantsDTO.class))).thenReturn(participants);

        ParticipantsEntity actualEntity = mapstruct.toEntity(participantsDTO);

        assertEquals(participantsDTO.getId(), actualEntity.getId());
        assertEquals(participantsDTO.getName(), actualEntity.getName());
        assertEquals(participantsDTO.getNumber(), actualEntity.getNumber());
    }
}