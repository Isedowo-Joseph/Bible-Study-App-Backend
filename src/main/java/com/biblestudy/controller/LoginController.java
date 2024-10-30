package com.biblestudy.controller;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.biblestudy.model.Credential;
import com.biblestudy.model.User;
import com.biblestudy.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credential loginRequest, HttpServletRequest request) {
        User user = userService.authenticate(loginRequest.getUserName(), loginRequest.getPassword());

        if (user != null) {
            // Optionally create a new session without invalidating existing sessions
            HttpSession session = request.getSession(true); // Create a new session
            session.setAttribute("user", user); // Store user data in the session
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // logic is wrong because when you login and when you refresh it sets user to
    // the user to the state before it creates biblestudysession
    @PostMapping("/register")
    public void register(@RequestBody User registerRequest, HttpServletRequest request) {

        HttpSession session = request.getSession(true); // Create a new session
        session.setAttribute("user", registerRequest); // Store user data in the session

    }

    @GetMapping("/session")
    public ResponseEntity<?> getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Retrieve the session, but don't create a new one if it
                                                         // doesn't exist
        if (session != null) {
            User user = (User) session.getAttribute("user"); // Get the user from the session
            if (user != null) {
                Optional<User> realUser = userService.findUserById(user.getId());
                return ResponseEntity.ok(realUser);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user logged in");
    }
}
