package org.example.repository;

import org.example.model.EventsEntity;

import java.sql.SQLException;
import java.util.List;

public interface EventsRepository<T, K> {
    T findById(K id);

    boolean deleteById(K id);

    List<EventsEntity> findAll() throws SQLException, ClassNotFoundException;

    T save(T t);
}
