package org.example.util.json.jsonEventsTag;

import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;

import java.util.List;

public interface JsonConvectorTag {

    String toJson(EventsTagDTO eventsTagDTO);

    String toJson(List<EventsTagDTO> eventsTagDTOS);

    EventsTagDTO toDTO(String json);
}
