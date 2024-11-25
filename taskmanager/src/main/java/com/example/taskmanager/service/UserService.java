package com.example.taskmanager.service;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

@Autowired
    private UserRepository userRepository; //dependency injection to create objects-->componentclass


    public void saveEntry(User user){
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);

    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
       return userRepository.findByUserName(username);
    }
}
