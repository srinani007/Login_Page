package com.example.login_system.controller;

import com.example.login_system.model.User;
import com.example.login_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController  // ✅ Use @RestController for JSON responses
@RequestMapping("/api")  // ✅ API should be accessible at /api/register
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // ✅ Ensure you have this bean configured in a SecurityConfig class

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        System.out.println("Received user data: " + user); // 🔥 Debugging print

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty!");
        }

        // ✅ Encrypt the password before saving to DB
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
