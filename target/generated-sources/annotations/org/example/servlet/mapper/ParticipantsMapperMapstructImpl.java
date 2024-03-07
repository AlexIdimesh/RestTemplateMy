package org.example.servlet.mapper;

import javax.annotation.processing.Generated;
import org.example.model.ParticipantsEntity;
import org.example.servlet.dto.ParticipantsDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-07T21:05:11+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
public class ParticipantsMapperMapstructImpl implements ParticipantsMapperMapstruct {

    @Override
    public ParticipantsDTO toDto(ParticipantsEntity participantsEntity) {
        if ( participantsEntity == null ) {
            return null;
        }

        ParticipantsDTO participantsDTO = new ParticipantsDTO();

        participantsDTO.setId( participantsEntity.getId() );
        participantsDTO.setName( participantsEntity.getName() );
        participantsDTO.setNumber( participantsEntity.getNumber() );

        return participantsDTO;
    }

    @Override
    public ParticipantsEntity toEntity(ParticipantsDTO participantsDTO) {
        if ( participantsDTO == null ) {
            return null;
        }

        ParticipantsEntity participantsEntity = new ParticipantsEntity();

        participantsEntity.setId( participantsDTO.getId() );
        participantsEntity.setName( participantsDTO.getName() );
        participantsEntity.setNumber( participantsDTO.getNumber() );

        return participantsEntity;
    }
}
