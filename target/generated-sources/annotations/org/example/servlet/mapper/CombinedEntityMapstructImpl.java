package org.example.servlet.mapper;

import javax.annotation.processing.Generated;
import org.example.model.CombinedEntity;
import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;
import org.example.servlet.dto.CombinedEntityDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-07T21:05:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
public class CombinedEntityMapstructImpl implements CombinedEntityMapstruct {

    @Override
    public CombinedEntityDTO toDTO(CombinedEntity combinedEntity) {
        if ( combinedEntity == null ) {
            return null;
        }

        EventsEntity eventsEntity = null;
        EventsTagEntity eventsTagEntity = null;

        eventsEntity = combinedEntity.getEventsEntity();
        eventsTagEntity = combinedEntity.getEventsTagEntity();

        CombinedEntityDTO combinedEntityDTO = new CombinedEntityDTO( eventsEntity, eventsTagEntity );

        return combinedEntityDTO;
    }

    @Override
    public CombinedEntity toEntity(CombinedEntityDTO entityDTO) {
        if ( entityDTO == null ) {
            return null;
        }

        EventsEntity eventsEntity = null;
        EventsTagEntity eventsTagEntity = null;

        eventsEntity = entityDTO.getEventsEntity();
        eventsTagEntity = entityDTO.getEventsTagEntity();

        CombinedEntity combinedEntity = new CombinedEntity( eventsEntity, eventsTagEntity );

        return combinedEntity;
    }
}
