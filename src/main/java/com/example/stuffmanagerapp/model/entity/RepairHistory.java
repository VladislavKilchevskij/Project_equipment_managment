package com.example.stuffmanagerapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "maintenancehistory")
public class RepairHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String malfunction;
    @Column
    private String renderedService;
    @Column
    private BigDecimal serviceCoast;
    @ManyToOne
    @JoinColumn(name = "equipmentId")
    @ToString.Exclude
    private Equipment equipment;
    @ManyToOne
    @JoinColumn(name = "requestId")
    @ToString.Exclude
    private Request request;
}
