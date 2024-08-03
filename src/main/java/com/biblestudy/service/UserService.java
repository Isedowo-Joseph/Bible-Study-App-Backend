package com.biblestudy.service;

import com.biblestudy.model.BibleStudySession;
import com.biblestudy.model.CallSession;
import com.biblestudy.model.Friendship;
import com.biblestudy.model.User;
import com.biblestudy.repository.UserRepository;

import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            // Throw an exception if the task doesn't exist
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateTask(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setName(userDetails.getName());
        user.setUserName(userDetails.getUserName());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setMissedDays(userDetails.getMissedDays());
        user.setBibleStudySessionId(userDetails.getBibleStudySessionId());
        user.setFriends(userDetails.getFriends());
        user.setCallSessions(userDetails.getCallSessions());
    }

}
