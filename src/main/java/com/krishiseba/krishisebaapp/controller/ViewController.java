package com.krishiseba.krishisebaapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String homePage() {
        return "login";
    }

    @GetMapping("/farmer-dashboard")
    public String farmerDashboard() {
        return "farmer_dashboard";
    }

    @GetMapping("/provider-dashboard")
    public String providerDashboard() {
        return "providerDashboard";
    }

    //
    // THE CONFLICTING MAPPING FOR "/place-order" HAS BEEN REMOVED.
    // This is now handled exclusively by FarmerController, which is correct.
    //

    @GetMapping("/order-confirmation")
    public String orderConfirmation() {
        return "orderconformationpage";
    }

    // --- ADMIN PAGES ---
    @GetMapping("/admin-dashboard") public String adminDashboard() { return "AdminDashboard"; }
    @GetMapping("/admin-users") public String adminUsers() { return "adminUsers"; }
    @GetMapping("/admin-posts") public String adminPosts() { return "adminPosts"; }
    @GetMapping("/admin-reports") public String adminReports() { return "adminReports"; }
    @GetMapping("/admin-settings") public String adminSettings() { return "adminSettings"; }
    @GetMapping("/admin-messages") public String adminMessages() { return "adminMessages"; }

    // --- UTILITY PAGES ---
    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied";
    }
}