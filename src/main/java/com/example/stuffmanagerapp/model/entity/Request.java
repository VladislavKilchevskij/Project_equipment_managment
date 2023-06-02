package com.example.stuffmanagerapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Date regDate;
    @OneToOne(mappedBy = "request", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Maintenance maintenance;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employeeId")
    private Employee employee;
    @OneToMany(mappedBy = "request", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<RepairHistory> historyList;
}
