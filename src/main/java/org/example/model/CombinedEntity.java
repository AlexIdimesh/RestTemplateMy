package org.example.model;

public class CombinedEntity {
    private EventsEntity eventsEntity;

    private EventsTagEntity eventsTagEntity;

    public CombinedEntity(EventsEntity eventsEntity, EventsTagEntity eventsTagEntity) {
        this.eventsEntity = eventsEntity;
        this.eventsTagEntity = eventsTagEntity;
    }

    public EventsEntity getEventsEntity() {
        return eventsEntity;
    }


    public EventsTagEntity getEventsTagEntity() {
        return eventsTagEntity;
    }

    public void setEventsEntity(EventsEntity eventsEntity) {
        this.eventsEntity = eventsEntity;
    }

    public void setEventsTagEntity(EventsTagEntity eventsTagEntity) {
        this.eventsTagEntity = eventsTagEntity;
    }

    @Override
    public String toString() {
        return "CombinedEntity{" +
                "eventsEntity=" + eventsEntity +
                ", eventsTagEntity=" + eventsTagEntity +
                '}';
    }
}
