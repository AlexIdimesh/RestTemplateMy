package org.example.servlet.dto;

import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;

public class CombinedEntityDTO {
    private EventsEntity eventsEntity;

    private EventsTagEntity eventsTagEntity;

    public CombinedEntityDTO(EventsEntity eventsEntity, EventsTagEntity eventsTagEntity) {
        this.eventsEntity = eventsEntity;
        this.eventsTagEntity = eventsTagEntity;
    }

    public EventsEntity getEventsEntity() {
        return eventsEntity;
    }


    public EventsTagEntity getEventsTagEntity() {
        return eventsTagEntity;
    }

    @Override
    public String toString() {
        return "CombinedEntity{" +
                "eventsEntity=" + eventsEntity +
                ", eventsTagEntity=" + eventsTagEntity +
                '}';
    }
}
