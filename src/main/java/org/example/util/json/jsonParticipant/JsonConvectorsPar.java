package org.example.util.json.jsonParticipant;

import org.example.servlet.dto.ParticipantsDTO;

import java.util.List;

public interface JsonConvectorsPar {

    String toJson(ParticipantsDTO participantsDTO);

    String toJson(List<ParticipantsDTO> participantsDTOS);

    ParticipantsDTO toDTO(String json);
}
