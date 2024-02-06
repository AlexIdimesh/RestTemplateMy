package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.DBConnection;
import org.example.model.ParticipantsEntity;
import org.example.repository.rep.ext.ParticipantsEntityEntityRepositoryExt;
import org.example.repository.mapper.partipants.ParticipantsResultSetMapper;
import org.example.repository.mapper.partipants.ParticipantsResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipantsEntityEntityRepositoryImpl implements ParticipantsEntityEntityRepositoryExt {
    private ParticipantsResultSetMapper resultSetMapper;
    private ConnectionManager connectionManager;
    public ParticipantsEntityEntityRepositoryImpl() {
        this(new DBConnection(), new ParticipantsResultSetMapperImpl());
    }

    public ParticipantsEntityEntityRepositoryImpl(DBConnection dbConnection, ParticipantsResultSetMapperImpl participantsResultSetMapper) {
        this.connectionManager = dbConnection;
        this.resultSetMapper = participantsResultSetMapper;
    }

    @Override
    public ParticipantsEntity findById(Long id) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("SELECT * FROM participants WHERE participants_id = ?");
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ParticipantsEntity participantsEntity = null;
            if (resultSet.next()) {
                participantsEntity = resultSetMapper.map(resultSet);
            }
            return participantsEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<ParticipantsEntity> findByIdEvent(Long id) {
        List<ParticipantsEntity> entityList = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("SELECT p.* FROM events e JOIN participants p on e.events_id = p.events_id WHERE e.events_id = ?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ParticipantsEntity participantsEntity = resultSetMapper.map(resultSet);
                entityList.add(participantsEntity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return entityList;
    }

    @Override
    public boolean deleteById(Long id) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("DELETE FROM participants WHERE events_id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ParticipantsEntity> findAll() throws SQLException, ClassNotFoundException {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("SELECT * FROM  participants");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ParticipantsEntity> participantsEntities = new ArrayList<>();
            while (resultSet.next()) {
                participantsEntities.add(resultSetMapper.map(resultSet));
            }
            return participantsEntities;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ParticipantsEntity save(ParticipantsEntity participantsEntity) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("INSERT INTO participants(events_id,participants_name, participants_number) VALUES(?,?,?)");
            preparedStatement.setLong(1, participantsEntity.getId());
            preparedStatement.setString(2, participantsEntity.getName());
            preparedStatement.setString(3, participantsEntity.getNumber());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                participantsEntity.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Вставка неудачна, не удалось получить сгенерированный ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return participantsEntity;
    }

    @Override
    public ParticipantsEntity upDated(ParticipantsEntity participantsEntity) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("UPDATE participants SET participants_number = ?, participants_name = ? WHERE participants_id = ?");
            preparedStatement.setObject(1,participantsEntity.getNumber());
            preparedStatement.setObject(2,participantsEntity.getName());
            preparedStatement.setObject(3,participantsEntity.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
