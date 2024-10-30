package com.biblestudy.controller;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.Invitation;
import com.biblestudy.model.User;
import com.biblestudy.service.UserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    UserService userservice;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userservice, PasswordEncoder passwordEncoder) {
        this.userservice = userservice;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getUsers() {
        return this.userservice.findAllUsers();
    }

    @GetMapping(value = "/username/{username}")
    public User getUserByUserName(@PathVariable String username) {
        return this.userservice.findUserByUsername(username);

    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return this.userservice.findUserById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.userservice.saveUser(user);
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

    @DeleteMapping("/{userId}/removeFriend/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        this.userservice.removeFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/exitGroup/{bibleId}")
    public ResponseEntity<Void> exitGroup(@PathVariable Long userId, @PathVariable Long bibleId) {
        this.userservice.leaveGroup(userId, bibleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<User>> getUserFriendships(@PathVariable Long userId) {
        List<User> friendships = this.userservice.getFriendships(userId);
        return ResponseEntity.ok(friendships);
    }

    @GetMapping("/invitations/{userId}")
    public List<Invitation> getInvitations(@PathVariable Long userId) {
        return this.userservice.getInvitations(userId);
    }
}
