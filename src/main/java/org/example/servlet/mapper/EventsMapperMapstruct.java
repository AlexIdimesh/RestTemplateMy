package org.example.servlet.mapper;

import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventsMapperMapstruct {
    EventsMapperMapstruct INSTANCE = Mappers.getMapper(EventsMapperMapstruct.class);
    EventDTO toDTO (EventsEntity eventsEntity);
    EventsEntity toEntity(EventDTO eventDTO);
}
