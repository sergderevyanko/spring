package org.atomspace.learning.petspringangular.reactive.service;

import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Created by sergey.derevyanko on 21.05.19.
 */
@Service
public class TopicReactiveDataService {


    @Value("${server.port}")
    private String serverPort;

    WebClient webClient = WebClient.create("http://localhost:" + serverPort);

    public Mono<Topic> getTopic(Long id){
        return webClient.get().uri("api/topics/{id}", id).accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve().bodyToMono(Topic.class);
    }
}

