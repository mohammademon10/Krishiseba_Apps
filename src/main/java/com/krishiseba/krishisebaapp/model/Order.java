package com.krishiseba.krishisebaapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(name = "duration_days", nullable = false)
    private int durationDays;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    // --- THIS IS THE MISSING FIELD ---
    @Column(nullable = false)
    private String status;
}