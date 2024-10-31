package com.weilai.elasticsearch.controller;

import com.weilai.elasticsearch.entity.User;
import com.weilai.elasticsearch.repository.UserRepository;
import com.weilai.elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User savedUser = userService.save(newUser);
        return ResponseEntity.created(URI.create("/api/users/" + savedUser.getId())).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/searchByName")
    public List<User> searchByName(@RequestParam String Name) {
        return userRepository.findByName(Name);
    }
}