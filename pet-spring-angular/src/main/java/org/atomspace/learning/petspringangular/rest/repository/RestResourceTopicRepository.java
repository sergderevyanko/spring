package org.atomspace.learning.petspringangular.rest.repository;

import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by sergey.derevyanko on 20.05.19.
 */
@RepositoryRestResource
public interface RestResourceTopicRepository extends PagingAndSortingRepository<Topic, Long>{
}
