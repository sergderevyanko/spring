package org.atomspace.learning.petspringangular.repository;

import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sergey.derevyanko on 19.05.2019.
 */
@Repository
public interface TopicRepository extends CrudRepository<Topic, Long>, ExtendedRepository<Topic, Long> {
    List<Topic> findByDescriptionContains(String searchString);
}
