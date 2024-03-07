package org.example.servlet.mapper;

import javax.annotation.processing.Generated;
import org.example.model.EventsEntity;
import org.example.servlet.dto.EventDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-07T21:05:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
public class EventsMapperMapstructImpl implements EventsMapperMapstruct {

    @Override
    public EventDTO toDTO(EventsEntity eventsEntity) {
        if ( eventsEntity == null ) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();

        eventDTO.setId( eventsEntity.getId() );
        eventDTO.setName( eventsEntity.getName() );
        eventDTO.setCity( eventsEntity.getCity() );

        return eventDTO;
    }

    @Override
    public EventsEntity toEntity(EventDTO eventDTO) {
        if ( eventDTO == null ) {
            return null;
        }

        EventsEntity eventsEntity = new EventsEntity();

        eventsEntity.setId( eventDTO.getId() );
        eventsEntity.setName( eventDTO.getName() );
        eventsEntity.setCity( eventDTO.getCity() );

        return eventsEntity;
    }
}
