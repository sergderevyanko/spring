package org.atomspace.learning.petspringangular.configuration;

import org.atomspace.learning.petspringangular.repository.ExtendedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by sergey.derevyanko on 19.05.2019.
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.atomspace.learning.petspringangular.repository", repositoryBaseClass = ExtendedRepositoryImpl.class)
public class ExtendedRepositoryConfiguration {
    //additional JPA configuration here
}
