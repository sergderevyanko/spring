package org.atomspace.learning.petspringangular.util;

import org.atomspace.learning.petspringangular.dbmodel.User;
import org.atomspace.learning.petspringangular.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by sergey.derevyanko on 27.05.19.
 */
public class DatabaseDataLoader implements CommandLineRunner{

        @Autowired
        private UserRepository userRepository;

        @Override
        public void run(String... strings) throws Exception {
        loadTestUsers();
    }

    private void loadTestUsers(){
        List<User> usersToLoad = new ArrayList();
        usersToLoad.add(new User("admin", "Administrator", "admin@test.org", "test"));
        usersToLoad.add(new User("mmouse", "Mike Mouse", "mmouse@test.org", "test"));
        usersToLoad.stream().forEach(user -> {
            Optional<User> existedUser = this.userRepository.findByLogin(user.getLogin());
            if(existedUser.isPresent()){
                user.setId(existedUser.get().getId());
            }
            this.userRepository.save(user);
        });

    }
}
