package com.example.login_system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {


    

    @GetMapping("/loginPage")
    public String showLoginPage() {
        return "loginPage"; // Return a different view name
    }

    @PostMapping("/loginPage")
    public String handleLogin(@RequestParam String username, @RequestParam String password) {
        // Handle login logic here
        return "redirect:/home"; // Redirect to another page after successful login
    }
}
