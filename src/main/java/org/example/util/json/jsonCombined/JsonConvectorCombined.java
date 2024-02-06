package org.example.util.json.jsonCombined;

import org.example.servlet.dto.CombinedEntityDTO;
import org.example.servlet.dto.EventDTO;

import java.util.List;

public interface JsonConvectorCombined {

    String toJson(CombinedEntityDTO combinedEntityDTO);

    String toJson(List<CombinedEntityDTO> list);

    CombinedEntityDTO toDTO(String json);
}
