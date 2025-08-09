package com.krishiseba.krishisebaapp.repository;

import com.krishiseba.krishisebaapp.model.Equipment;
import com.krishiseba.krishisebaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    // Custom query to find all equipment listed by a specific provider
    List<Equipment> findByProvider(User provider);
}