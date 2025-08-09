package com.krishiseba.krishisebaapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class DashboardController {

    /**
     * This is the central dashboard handler. After a user successfully logs in,
     * Spring Security redirects them to this "/dashboard" URL as configured in SecurityConfig.
     * This method checks the user's role and redirects them to the correct
     * specific dashboard page (farmer, provider, or admin), which is then handled by the ViewController.
     */
    @GetMapping("/dashboard")
    public String handleDashboardRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();

                if (role.equals("ROLE_ADMIN")) {
                    return "redirect:/admin-dashboard";
                } else if (role.equals("ROLE_PROVIDER")) {
                    return "redirect:/provider-dashboard";
                } else if (role.equals("ROLE_FARMER")) {
                    return "redirect:/farmer-dashboard";
                }
            }
        }
        // If for some reason the user has no role or isn't authenticated,
        // send them back to the login page with an error.
        return "redirect:/login?error=true";
    }
}