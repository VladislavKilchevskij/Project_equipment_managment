package com.example.stuffmanagerapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Acceptance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Date acceptanceDate;
    @Column
    private String provider;
    @Column
    private BigDecimal totalCoast;
    @OneToMany(mappedBy = "acceptance", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Equipment> equipment = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "employeeId")
    @ToString.Exclude
    private Employee employee;
}
