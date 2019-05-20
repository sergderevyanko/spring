package org.atomspace.learning.petspringangular.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sergey.derevyanko on 19.05.2019.
 */
public interface ExtendedRepository <T, ID extends Serializable>{

    List<T> findByAttributeContainsText(String attributeName, String text);

    T updateWith(T with, ID id);
}
