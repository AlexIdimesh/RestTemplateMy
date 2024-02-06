package org.example.servlet.mapper;
import org.example.model.ParticipantsEntity;
import org.example.servlet.dto.ParticipantsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ParticipantsMapperMapstruct {

    ParticipantsMapperMapstruct INSTANCE = Mappers.getMapper(ParticipantsMapperMapstruct.class);

    ParticipantsDTO toDto(ParticipantsEntity participantsEntity);

    ParticipantsEntity toEntity(ParticipantsDTO participantsDTO);
}
