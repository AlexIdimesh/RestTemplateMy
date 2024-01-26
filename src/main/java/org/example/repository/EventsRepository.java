package org.example.repository;

import java.util.Optional;

public interface EventsRepository<T, K> {
    T findById(K id);

    boolean deleteById(K id);

    T findAll();

    T save(T t);
}
