package org.atomspace.learning.petspringangular;

import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.atomspace.learning.petspringangular.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by sergey.derevyanko on 19.05.2019.
 */
@RestController
@CrossOrigin("http://localhost:4200")
public class TopicController {

    @Autowired
    TopicRepository topicRepository;

    @GetMapping("/topics")
    public List<Topic> search(@RequestParam(value = "searchString", required = false) String searchString){
        if(searchString == null || searchString.isEmpty()){
            return topicRepository.findAll();
        }
        return topicRepository.findByAttributeContainsText("description", searchString);
    }
    @GetMapping("/topic/{id}")
    public Optional<Topic> findTopic(@PathVariable Long id){
        return topicRepository.findById(id);
    }

    @PostMapping(value = "/topics")
    public ResponseEntity createTopic(@RequestBody Topic topic){
        Topic newTopic = topicRepository.save(topic);
        return new ResponseEntity(newTopic, HttpStatus.OK);
    }

    @PutMapping(value = "/topic/{id}")
    public ResponseEntity updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        Optional<Topic> topicForUpdate = topicRepository.findById(id);
        Topic updatedTopic;
        if(topicForUpdate.isPresent()){
            updatedTopic = topicRepository.save(topic);
        }
        else {
            return new ResponseEntity("No topic found with id = "+ id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedTopic, HttpStatus.OK);
    }

    @PatchMapping(value = "/topic/{id}")
    public ResponseEntity patchTopic(@PathVariable Long id, @RequestBody Topic topic){
        Topic updatedTopic = topicRepository.updateWith(topic, id);
        if(updatedTopic == null ){
            return new ResponseEntity("No topic found with id = "+ id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedTopic, HttpStatus.OK);
    }

}
