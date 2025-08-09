package com.krishiseba.krishisebaapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    private String brand;

    // This is the corrected part. The column in the database will be named "equipment_condition"
    // to avoid conflict with the SQL reserved keyword "condition".
    @Column(name = "equipment_condition")
    private String condition;

    @Column(nullable = false)
    private BigDecimal pricePerDay;

    @Column(nullable = false)
    private String location;

    @Lob // For longer text descriptions
    private String description;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider; // The User who owns this equipment
}