package org.example.util.json.jsonEvent;

import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;

import java.util.List;

public interface JsonConvector {

    String toJson(EventDTO eventDTO);

    String toJson(List<EventDTO> eventDTOS);

    EventDTO toDTO(String json);
}
