package org.ssau.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ssau.model.User;
import org.ssau.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping({"/users"})
public class UserService {

    @Autowired
    private UserRepository users;

    @PostMapping
    public User create(@RequestBody User u){
        User _u = users.save(new User(u.getLogin(), u.getPassword(), u.isAdmin()));
        return _u;
    }

    @GetMapping(path = {"/{login}"})
    public User findOne(@PathVariable("login") String login){
        for(User u : users.findAll())
            if( u.getLogin().equalsIgnoreCase(login) )
                return u;
        return null;
    }

    @GetMapping
    public java.lang.Iterable findAll(){
        return users.findAll();
    }
}
