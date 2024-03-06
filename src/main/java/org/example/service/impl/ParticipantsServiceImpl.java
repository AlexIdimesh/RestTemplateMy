package org.example.service.impl;

import org.example.model.ParticipantsEntity;
import org.example.repository.impl.ParticipantsEntityRepositoryImpl;
import org.example.repository.rep.ext.ParticipantsEntityEntityRepositoryExt;
import org.example.service.serverImpl.ParticipantsService;
import org.example.servlet.dto.ParticipantsDTO;
import org.example.servlet.mapper.ParticipantsMapperMapstruct;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ParticipantsServiceImpl implements ParticipantsService {

    private ParticipantsEntityEntityRepositoryExt repository = new ParticipantsEntityRepositoryImpl();
    private ParticipantsMapperMapstruct mapper = ParticipantsMapperMapstruct.INSTANCE;

    public ParticipantsServiceImpl(ParticipantsEntityEntityRepositoryExt repository, ParticipantsMapperMapstruct mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ParticipantsServiceImpl() {
    }

    @Override
    public ParticipantsDTO save(ParticipantsDTO participantsDTO) {
        return mapper.toDto(repository.save(mapper.toEntity(participantsDTO)));
    }

    @Override
    public ParticipantsDTO findById(Long id) {
        ParticipantsEntity participantsEntity = repository.findById(id).orElse(null);
        return mapper.toDto(participantsEntity);
    }

    @Override
    public List<ParticipantsDTO> findByIdEvents(Long id) {
        List<ParticipantsEntity> participants = repository.findByIdEvent(id);
        return participants.stream().map(mapper :: toDto).collect(toList());
    }

    @Override
    public List<ParticipantsDTO> findAll() throws SQLException, ClassNotFoundException {
        return repository.findAll().stream().map(mapper :: toDto).collect(toList());
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public ParticipantsDTO upDated(ParticipantsDTO participantsDTO) {
        return mapper.toDto(repository.upDated(mapper.toEntity(participantsDTO)));
    }
}
