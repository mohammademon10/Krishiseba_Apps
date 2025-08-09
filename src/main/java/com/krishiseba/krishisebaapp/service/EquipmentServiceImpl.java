package com.krishiseba.krishisebaapp.service;

import com.krishiseba.krishisebaapp.model.Equipment;
import com.krishiseba.krishisebaapp.model.Order; // <-- Import Order
import com.krishiseba.krishisebaapp.model.User;
import com.krishiseba.krishisebaapp.repository.EquipmentRepository;
import com.krishiseba.krishisebaapp.repository.OrderRepository; // <-- Import OrderRepository
import com.krishiseba.krishisebaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository; // <-- Add the OrderRepository

    @Autowired
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository; // <-- Initialize it
    }

    @Override
    public void saveEquipment(Equipment equipment, String providerEmail) {
        User provider = userRepository.findByEmail(providerEmail)
                .orElseThrow(() -> new IllegalStateException("Provider not found with email: " + providerEmail));
        equipment.setProvider(provider);
        equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> findByProvider(User provider) {
        return equipmentRepository.findByProvider(provider);
    }

    // This is the implementation of the new method
    @Override
    public Order placeOrder(Order order) {
        // You can add more logic here later, like checking availability
        return orderRepository.save(order);
    }
}