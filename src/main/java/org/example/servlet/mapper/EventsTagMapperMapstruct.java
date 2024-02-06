package org.example.servlet.mapper;

import org.example.model.EventsTagEntity;
import org.example.servlet.dto.EventsTagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface EventsTagMapperMapstruct {

    EventsTagMapperMapstruct INSTANCE = Mappers.getMapper(EventsTagMapperMapstruct.class);

    EventsTagDTO toDto(EventsTagEntity tagEntity);

    EventsTagEntity toEntity(EventsTagDTO dto);
}
