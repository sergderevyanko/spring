package org.atomspace.learning.petspringangular.reactive.controller;

import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.atomspace.learning.petspringangular.reactive.service.TopicReactiveDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by sergey.derevyanko on 21.05.19.
 */
@RestController
public class ReactiveTopicController {
    @Autowired
    TopicReactiveDataService topicDataService;

    @GetMapping("/api/v2/topics/{id}")
    public Mono<Topic> getTopic(@PathVariable Long id){
        return topicDataService.getTopic(id);
    }
}
