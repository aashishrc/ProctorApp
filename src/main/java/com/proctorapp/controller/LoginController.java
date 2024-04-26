package com.proctorapp.controller;

import com.proctorapp.dao.UserDao;
import com.proctorapp.model.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Validate username and password
        Users user = userDao.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "dashboard";
        } else {
            // Authentication failed, redirect back to login page with an error message
            return "login-error";
        }
    }
}
