package org.example.servlet.mapper;

import javax.annotation.processing.Generated;
import org.example.model.EventsTagEntity;
import org.example.servlet.dto.EventsTagDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-06T17:06:53+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 15.0.10 (Azul Systems, Inc.)"
)
public class EventsTagMapperMapstructImpl implements EventsTagMapperMapstruct {

    @Override
    public EventsTagDTO toDto(EventsTagEntity tagEntity) {
        if ( tagEntity == null ) {
            return null;
        }

        EventsTagDTO eventsTagDTO = new EventsTagDTO();

        eventsTagDTO.setId( tagEntity.getId() );
        eventsTagDTO.setTagName( tagEntity.getTagName() );
        eventsTagDTO.setTagAuthor( tagEntity.getTagAuthor() );

        return eventsTagDTO;
    }

    @Override
    public EventsTagEntity toEntity(EventsTagDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EventsTagEntity eventsTagEntity = new EventsTagEntity();

        eventsTagEntity.setId( dto.getId() );
        eventsTagEntity.setTagName( dto.getTagName() );
        eventsTagEntity.setTagAuthor( dto.getTagAuthor() );

        return eventsTagEntity;
    }
}
