package org.example.servlet.mapper;

import org.example.model.CombinedEntity;
import org.example.servlet.dto.CombinedEntityDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
class CombinedEntityMapstructImplTest {

    private CombinedEntityMapstruct mapstruct = mock(CombinedEntityMapstruct.class);

    @Test
    void testToDTO() {
        CombinedEntity combined = mock(CombinedEntity.class);
        CombinedEntityDTO expectedDTO = mock(CombinedEntityDTO.class);

        when(mapstruct.toDTO(any(CombinedEntity.class))).thenReturn(expectedDTO);

        CombinedEntityDTO actualDTO = mapstruct.toDTO(combined);

        assertEquals(expectedDTO.getEventsEntity(), actualDTO.getEventsEntity());
        assertEquals(expectedDTO.getEventsTagEntity(), actualDTO.getEventsTagEntity());
    }

    @Test
    void testToEntity() {
        CombinedEntityDTO expectedDTO = mock(CombinedEntityDTO.class);
        CombinedEntity combined = mock(CombinedEntity.class);

        when(mapstruct.toEntity(any(CombinedEntityDTO.class))).thenReturn(combined);

        CombinedEntity actual = mapstruct.toEntity(expectedDTO);

        assertEquals(expectedDTO.getEventsEntity(), actual.getEventsEntity());
        assertEquals(expectedDTO.getEventsTagEntity(), actual.getEventsTagEntity());
    }
}