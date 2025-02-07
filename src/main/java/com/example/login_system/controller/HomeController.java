package com.example.login_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";  // ✅ Redirect to home.html
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";  // ✅ Redirect to logout.html
    }
}
