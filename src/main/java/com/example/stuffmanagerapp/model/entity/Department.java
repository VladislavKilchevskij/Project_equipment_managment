package com.example.stuffmanagerapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String departmentName;
    @Column
    private Integer floor;
    @Column
    private String phoneNumber;
    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private List<Employee> employees;
}
