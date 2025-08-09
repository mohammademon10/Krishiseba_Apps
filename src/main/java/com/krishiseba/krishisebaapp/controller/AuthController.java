package com.krishiseba.krishisebaapp.controller;

import com.krishiseba.krishisebaapp.model.Role;
import com.krishiseba.krishisebaapp.model.User;
import com.krishiseba.krishisebaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "Registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("userType") String userType) {
        try {
            if ("farmer".equalsIgnoreCase(userType)) {
                user.setRole(Role.ROLE_FARMER);
            } else if ("provider".equalsIgnoreCase(userType)) {
                user.setRole(Role.ROLE_PROVIDER);
            } else {
                return "redirect:/register?error=true";
            }
            userService.registerNewUser(user);
        } catch (Exception e) {
            return "redirect:/register?error=true";
        }
        return "redirect:/login?success=true";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }
}