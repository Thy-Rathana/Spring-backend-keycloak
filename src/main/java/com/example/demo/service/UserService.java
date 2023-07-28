package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    public User saveUser(User user) {
        return repo.save(user);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }


    public User isDataExist(User reqData) {
        return repo.findByEmailAndMobNo(reqData.getEmail(), reqData.getMobNo());
    }

    public User getUserById(String id) {
        return repo.findById(id).orElse(null);
    }

    public User getUserByEmailAndMobNo(String email, String mobNo) {
        return repo.findByEmailAndMobNo(email, mobNo);
    }

    public String deleteUser(String id) {
        repo.deleteById(id);
        return "User deleted successfully";
    }

    public User updateUser(User user) {
        User existingUser = repo.findById(user.getId()).orElse(null);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobNo(user.getMobNo());
        existingUser.setPassword(user.getPassword());
        return repo.save(existingUser);
    }




}