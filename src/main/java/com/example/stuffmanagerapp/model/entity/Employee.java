package com.example.stuffmanagerapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String surname;
    @Column
    private String firstName;
    @Column
    private String patronymic;
    @Column
    private String position;
    @Column
    private boolean isChief = false;
    @ManyToOne
    @JoinColumn(name = "departmentId")
    @ToString.Exclude
    private Department department;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Equipment> equipment = new ArrayList<>();
    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private List<Acceptance> acceptances;
    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private List<Writeoff> writeoffs;
    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private List<Request> requests;
    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private List<Maintenance> maintenances;
}
