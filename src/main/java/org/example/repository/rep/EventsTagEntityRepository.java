package org.example.repository.rep;

import java.sql.SQLException;
import java.util.List;

public interface EventsTagEntityRepository <T,K> {

    T findById(K id);

    boolean deleteById(K id);

    List<T> findAll() throws SQLException, ClassNotFoundException;

    T save(T t);

    T upDated(T t);
}
