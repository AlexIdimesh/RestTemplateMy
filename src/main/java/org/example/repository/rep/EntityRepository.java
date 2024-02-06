package org.example.repository.rep;

import org.example.model.CombinedEntity;
import org.example.model.EventsEntity;
import org.example.model.EventsTagEntity;

import java.sql.SQLException;
import java.util.List;

public interface EntityRepository<T, K> {
    T findById(K id);

    List<CombinedEntity> findAllEventsByEventTag(K id);

    boolean deleteById(K id);

    List<T> findAll() throws SQLException, ClassNotFoundException;

    T save(T t);

    T upDated(T t);
}
