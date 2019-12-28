package com.he.repositories;

import com.he.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLogin(String login);

}
