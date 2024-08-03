package com.biblestudy.controller;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.User;
import com.biblestudy.service.UserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:1306")
@RestController
@RequestMapping("/user")
public class UserController {
    UserService userservice;

    @Autowired
    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping
    public List<User> getUsers() {
        return this.userservice.findAllUsers();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(Long id) {
        return this.userservice.findUserById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibleStudySession> updateUser(@PathVariable Long id,
            @RequestBody User user) {
        try {
            this.userservice.updateUser(id, user);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            this.userservice.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
