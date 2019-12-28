package com.he.server.repositories;

import java.util.Optional;

public interface CrudRepository<T,ID> {
    Optional<T> findOne(ID id);

    int save(T t);
}
