package com.weilai.elasticsearch.controller;

import com.weilai.elasticsearch.entity.User;
import com.weilai.elasticsearch.repository.UserRepository;
import com.weilai.elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

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
    public List<User> searchByName(@RequestParam String name) {
        return userRepository.findByName(name);
    }

    @GetMapping("/searchByNameLike")
    public List<User> searchByNameLike(@RequestParam String name) {
        return userRepository.findByNameLike(name);
    }


    /**
     * create these records and only when id =7、9, it returns records.
     * haven't understood the principle behind the similarity judgment yet.
     * [
     *   {
     *     "id": "2",
     *     "name": "w2",
     *     "email": "w2"
     *   },
     *   {
     *     "id": "3",
     *     "name": "w2",
     *     "email": "w2"
     *   },
     *   {
     *     "id": "4",
     *     "name": "w2",
     *     "email": "w2"
     *   },
     *   {
     *     "id": "1",
     *     "name": "w1",
     *     "email": "w1"
     *   },
     *   {
     *     "id": "5",
     *     "name": "w1 w2 w3 w4 s1 s2",
     *     "email": "w1"
     *   },
     *   {
     *     "id": "6",
     *     "name": "w1 w2 w3 w4 s1 s2",
     *     "email": "w1"
     *   },
     *   {
     *     "id": "7",
     *     "name": "s1 s2 w2 w2",
     *     "email": "w1"
     *   },
     *   {
     *     "id": "8",
     *     "name": "s1 s2 w2 w3",
     *     "email": "w1"
     *   },
     *   {
     *     "id": "9",
     *     "name": "s1 s2 w2 s2",
     *     "email": "w1"
     *   }
     * ]
     *
     *
     */
    @GetMapping("/searchSimilarByName")
    public Page<User> searchSimilarByName(@RequestParam String id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            return Page.empty();
        }
        User user = userOptional.get();

        return userRepository.searchSimilar(user, new String[]{"name"}, PageRequest.of(0,20));
    }

}