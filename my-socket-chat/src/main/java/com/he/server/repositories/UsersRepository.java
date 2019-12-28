package com.he.server.repositories;

import com.he.server.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLogin(String login);

}
