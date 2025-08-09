package com.krishiseba.krishisebaapp.controller;

import com.krishiseba.krishisebaapp.model.User;
import com.krishiseba.krishisebaapp.service.KrishiSebaUserDetailsService;
import com.krishiseba.krishisebaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    private final UserService userService;

    @Autowired
    public ProviderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProviderProfile(Model model, @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {
        model.addAttribute("user", customUserDetails.getUser());
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProviderProfile(@ModelAttribute User user, @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {
        userService.updateUserProfile(customUserDetails.getUsername(), user.getFullName(), user.getPhone(), user.getAddress());
        return "redirect:/provider/profile?success=profile_updated";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {

        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/provider/profile?error=match";
        }

        // THIS IS THE FIX: We now pass the user's email (a String) from the customUserDetails object.
        boolean changed = userService.changePassword(customUserDetails.getUsername(), currentPassword, newPassword);

        if (changed) {
            return "redirect:/provider/profile?success=password_changed";
        } else {
            return "redirect:/provider/profile?error=incorrect_current_password";
        }
    }

    @GetMapping("/orders") public String showProviderOrders() { return "myOrders"; }
    @GetMapping("/messages") public String showProviderMessages() { return "myMessages"; }
}