package org.example.repository.rep;

import org.example.model.CombinedEntity;
import org.example.model.EventsEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EntityRepository<T, K> {
    Optional<EventsEntity> findById(K id);

    List<CombinedEntity> findAllEventsByEventTag(K id);

    boolean deleteById(K id);

    List<T> findAll() throws SQLException, ClassNotFoundException;

    T save(T t);

    T upDated(T t);
}
