package org.atomspace.learning.petspringangular.repository;

import org.atomspace.learning.petspringangular.dbmodel.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by sergey.derevyanko on 27.05.19.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByLogin(String login);

    public List<User> findAll();
}
