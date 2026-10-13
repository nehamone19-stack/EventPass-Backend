package com.example.EventPass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Users addUser(@RequestBody Users user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<Users> getUsers() {
        return userRepository.findAll();
    }
}