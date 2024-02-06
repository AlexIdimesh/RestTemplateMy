package org.example.servlet.mapper;

import org.example.model.CombinedEntity;
import org.example.servlet.dto.CombinedEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface CombinedEntityMapstruct {
    CombinedEntityMapstruct INSTANCE = Mappers.getMapper(CombinedEntityMapstruct.class);

    CombinedEntityDTO toDTO (CombinedEntity combinedEntity);
    CombinedEntity toEntity(CombinedEntityDTO entityDTO);
}
