package com.example.stuffmanagerapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private BigDecimal totalServiceCost;
    @Column
    private Date serviceDate;
    @OneToOne
    @JoinColumn(name = "requestId")
    @ToString.Exclude
    private Request request;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employeeId")
    @ToString.Exclude
    private Employee employee;
}
