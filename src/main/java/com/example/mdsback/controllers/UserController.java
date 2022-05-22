package com.example.mdsback.controllers;

import com.example.mdsback.DTOs.UserDTO;
import com.example.mdsback.models.User;
import com.example.mdsback.repositories.UserRepository;
import com.example.mdsback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/id/{id}")
    public UserDTO findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return new UserDTO(user);
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @CrossOrigin
    @PutMapping("/sample/{userId}/{sampleId}")
    public UserDTO addSampleToUser(@PathVariable Long userId, @PathVariable Long sampleId) {
        return new UserDTO(userService.addSampleToUser(userId, sampleId));
    }

    @CrossOrigin
    @PutMapping("/harmony{userId}/{harmonyId}")
    public UserDTO addHarmonyToUser(@PathVariable Long userId, @PathVariable Long harmonyId) {
        return new UserDTO(userService.addHarmonyToUser(userId, harmonyId));
    }

    @CrossOrigin
    @DeleteMapping("{name}")
    public long delete(@PathVariable String name){
        return userService.delete(name);
    }


}
