package com.krishiseba.krishisebaapp.controller;

import com.krishiseba.krishisebaapp.model.Equipment;
import com.krishiseba.krishisebaapp.model.User;
import com.krishiseba.krishisebaapp.repository.EquipmentRepository;
import com.krishiseba.krishisebaapp.repository.UserRepository;
import com.krishiseba.krishisebaapp.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentController(EquipmentService equipmentService, UserRepository userRepository, EquipmentRepository equipmentRepository) {
        this.equipmentService = equipmentService;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping("/add-equipment")
    public String showAddEquipmentForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        return "addEquipment";
    }

    @PostMapping("/add-equipment")
    public String addEquipment(@ModelAttribute("equipment") Equipment equipment, @AuthenticationPrincipal UserDetails userDetails) {
        String providerEmail = userDetails.getUsername();
        equipmentService.saveEquipment(equipment, providerEmail);
        return "redirect:/my-equipment";
    }

    @GetMapping("/my-equipment")
    public String showMyEquipmentPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String providerEmail = userDetails.getUsername();
        User provider = userRepository.findByEmail(providerEmail)
                .orElseThrow(() -> new IllegalStateException("Provider not found with email: " + providerEmail));
        List<Equipment> myEquipmentList = equipmentService.findByProvider(provider);
        model.addAttribute("equipmentList", myEquipmentList);
        return "myequipment";
    }

    @GetMapping("/equipment-marketplace")
    public String showMarketplace(Model model) {
        List<Equipment> allEquipment = equipmentRepository.findAll();
        model.addAttribute("equipmentList", allEquipment);
        return "equipment";
    }
}