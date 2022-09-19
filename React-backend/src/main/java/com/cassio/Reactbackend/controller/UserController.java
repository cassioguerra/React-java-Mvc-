package com.cassio.Reactbackend.controller;

import com.cassio.Reactbackend.exception.UserNotFoundException;
import com.cassio.Reactbackend.model.UserModel;
import com.cassio.Reactbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    UserModel newUser(@RequestBody UserModel newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<UserModel> getAllUser(){
        return userRepository.findAll();
    }

      
        @GetMapping("/user/{id}")
        UserModel getUserById(@PathVariable Long id) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));
        }
          
        
        @PutMapping("/user/{id}")
        UserModel updateUser(@RequestBody UserModel newUser, @PathVariable Long id) {
            return userRepository.findById(id)
                    .map(user -> {
                        user.setUsername(newUser.getUsername());
                        user.setName(newUser.getName());
                        user.setEmail(newUser.getEmail());
                        return userRepository.save(user);
                    }).orElseThrow(() -> new UserNotFoundException(id));
        }

        @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }
    }

