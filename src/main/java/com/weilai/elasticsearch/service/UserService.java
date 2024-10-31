package com.weilai.elasticsearch.service;

import com.weilai.elasticsearch.entity.User;
import com.weilai.elasticsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        Iterable<User> users = userRepository.findAll();

        List<User> list = new ArrayList<>();
        users.forEach(list::add);
        return list;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
