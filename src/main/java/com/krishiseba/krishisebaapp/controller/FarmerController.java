package com.krishiseba.krishisebaapp.controller;

import com.krishiseba.krishisebaapp.model.Equipment;
import com.krishiseba.krishisebaapp.model.Order;
import com.krishiseba.krishisebaapp.model.User;
import com.krishiseba.krishisebaapp.repository.EquipmentRepository;
import com.krishiseba.krishisebaapp.service.EquipmentService;
import com.krishiseba.krishisebaapp.service.KrishiSebaUserDetailsService;
import com.krishiseba.krishisebaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class FarmerController {

    private final UserService userService;
    private final EquipmentService equipmentService;
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public FarmerController(UserService userService, EquipmentService equipmentService, EquipmentRepository equipmentRepository) {
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.equipmentRepository = equipmentRepository;
    }

    // This is the method with the definitive fix
    @GetMapping("/place-order")
    public String showOrderForm(Model model, @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {
        model.addAttribute("order", new Order());
        model.addAttribute("equipmentList", equipmentRepository.findAll());

        // THE FIX: We get the full user object (which includes the phone number)
        // from the customUserDetails and pass it to the model.
        User currentUser = customUserDetails.getUser();
        model.addAttribute("currentUser", currentUser);

        return "orderForFarmer";
    }

    @PostMapping("/place-order")
    public String submitOrder(@ModelAttribute Order order, @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {
        User farmer = customUserDetails.getUser();
        order.setFarmer(farmer);

        Equipment selectedEquipment = equipmentRepository.findById(order.getEquipment().getId()).orElse(null);
        order.setEquipment(selectedEquipment);

        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");

        equipmentService.placeOrder(order);
        return "redirect:/order-confirmation";
    }

    // ... rest of the FarmerController methods are correct ...
    @GetMapping("/farmer/profile")
    public String showFarmerProfile(Model model, @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {
        model.addAttribute("user", customUserDetails.getUser());
        return "profile";
    }

    @PostMapping("/farmer/profile/update")
    public String updateFarmerProfile(@ModelAttribute User user, @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {
        userService.updateUserProfile(customUserDetails.getUsername(), user.getFullName(), user.getPhone(), user.getAddress());
        return "redirect:/farmer/profile?success=profile_updated";
    }

    @PostMapping("/farmer/profile/change-password")
    public String changeFarmerPassword(@RequestParam("currentPassword") String currentPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       @AuthenticationPrincipal KrishiSebaUserDetailsService.CustomUserDetails customUserDetails) {
        if (!newPassword.equals(confirmPassword)) { return "redirect:/farmer/profile?error=match"; }
        boolean changed = userService.changePassword(customUserDetails.getUsername(), currentPassword, newPassword);
        if (changed) { return "redirect:/farmer/profile?success=password_changed"; }
        else { return "redirect:/farmer/profile?error=incorrect_current_password"; }
    }

    @GetMapping("/farmer/requests") public String showFarmerRequests() { return "requests"; }
    @GetMapping("/farmer/orders") public String showFarmerOrders() { return "showFarmerorder"; }
    @GetMapping("/farmer/messages") public String showFarmerMessages() { return "messages"; }
}