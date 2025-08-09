package com.krishiseba.krishisebaapp.service;

import com.krishiseba.krishisebaapp.model.Equipment;
import com.krishiseba.krishisebaapp.model.User;
import com.krishiseba.krishisebaapp.model.Order; // <-- Import the Order class

import java.util.List;

public interface EquipmentService {

    void saveEquipment(Equipment equipment, String providerEmail);

    List<Equipment> findByProvider(User provider);

    // This is the new method for placing an order
    Order placeOrder(Order order);
}