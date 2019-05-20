package org.atomspace.learning.petspringangular.configuration;

import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

/**
 * Created by sergey.derevyanko on 20.05.19.
 */
@Configuration
public class RestRepositoryConfiguration {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(){
        return new RepositoryRestConfigurer() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.exposeIdsFor(Topic.class);
                config.setReturnBodyForPutAndPost(true);
            }
        };
    }
}
