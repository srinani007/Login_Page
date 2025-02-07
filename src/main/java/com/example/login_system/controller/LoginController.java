package com.example.login_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/loginPage")
    public String showLoginPage() {
        return "loginPage";  // ✅ Redirect to Thymeleaf login.html
    }
}
