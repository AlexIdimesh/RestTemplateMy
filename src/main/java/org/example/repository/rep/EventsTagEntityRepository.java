package org.example.repository.rep;

import org.example.model.EventsTagEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EventsTagEntityRepository <T,K> {

    Optional<EventsTagEntity> findById(K id);

    boolean deleteById(K id);

    List<T> findAll() throws SQLException, ClassNotFoundException;

    T save(T t);

    T upDated(T t);
}
