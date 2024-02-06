package org.example.repository.mapper.partipants;

import org.example.model.ParticipantsEntity;

import java.sql.ResultSet;

public interface ParticipantsResultSetMapper {

    ParticipantsEntity map(ResultSet resultSet);
}
