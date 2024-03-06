package org.example.repository.rep;

import org.example.model.ParticipantsEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ParticipantsRepository <T,K> {

    Optional<ParticipantsEntity> findById(K id);

    List<T> findByIdEvent(K id);

    boolean deleteById(K id);

    List<T> findAll() throws SQLException, ClassNotFoundException;

    T save(T t);

    T upDated(T t);

}
