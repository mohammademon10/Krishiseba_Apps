package com.krishiseba.krishisebaapp.repository;

import com.krishiseba.krishisebaapp.model.Order;
import com.krishiseba.krishisebaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Custom query to find all orders placed by a specific farmer
    List<Order> findByFarmer(User farmer);

    // Custom query to find all orders for equipment owned by a specific provider
    List<Order> findByEquipmentProvider(User provider);
}