package org.example.repository.mapper.partipants;


import org.example.model.ParticipantsEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantsResultSetMapperImpl implements ParticipantsResultSetMapper {
    @Override
    public ParticipantsEntity map(ResultSet resultSet) {
        try {
            ParticipantsEntity participantsEntity = new ParticipantsEntity();
            participantsEntity.setId(resultSet.getLong("participants_id"));
            participantsEntity.setName(resultSet.getString("participants_name"));
            participantsEntity.setNumber(resultSet.getString("participants_number"));
            return participantsEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
