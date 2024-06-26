package com.biblestudy.service;

import com.biblestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository uRepository){
        this.userRepository = uRepository;
    }
    public void registerUser(String name, String username,String password, String email, String phoneNumber){

    }
    public Boolean loginUser (String username, String password){
        return null;
    }
}
