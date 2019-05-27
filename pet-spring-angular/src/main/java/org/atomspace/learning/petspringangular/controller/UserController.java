package org.atomspace.learning.petspringangular.controller;

import org.atomspace.learning.petspringangular.dbmodel.User;
import org.atomspace.learning.petspringangular.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


/**
 * Created by sergey.derevyanko on 24.05.19.
 */
@RestController
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/users")
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @GetMapping("/status")
    public ResponseEntity<String> status(){
        return new ResponseEntity<String>("It works", HttpStatus.OK);
    }

    @GetMapping("/admin/userdetails")
    public User getUserDetails(Principal principal){
        return userRepository.findByLogin(principal.getName()).get();

    }

    @GetMapping("/auth_error")
    public ResponseEntity<String> authenticationError(){
        return new ResponseEntity<String>("Authentication failed", HttpStatus.UNAUTHORIZED);
    }


}
