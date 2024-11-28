package com.example.taskmanager.controller;

import com.example.taskmanager.entity.JournalEntry;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.service.JournalEntryService;
import com.example.taskmanager.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")  //end -point for api hitting
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
      return  userService.getAll();
    }

    @PostMapping
    public void createEntry(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
    User userInDb=userService.findByUserName(userName);
    if(userInDb!=null){
         userInDb.setUserName(user.getUserName());
         userInDb.setPassword(user.getPassword());
         userService.saveEntry(userInDb);
       }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
