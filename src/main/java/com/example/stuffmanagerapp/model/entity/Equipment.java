package com.example.stuffmanagerapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String category;
    @Column
    private String equipmentName;
    @Column
    private Integer inspectionTerm;
    @Column
    private Integer usageTerm;
    @Column
    private Date nextInspectionDate;
    @Column
    private Date usageEndDate;
    @Column
    private String status;
    @Column
    private BigDecimal coast;
    @Column
    private String writeoffReason;
    @ManyToOne
    @JoinColumn(name = "employeeId")
    @ToString.Exclude
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "acceptanceId")
    @ToString.Exclude
    private Acceptance acceptance;
    @ManyToOne
    @JoinColumn(name = "writeoffId")
    @ToString.Exclude
    private Writeoff writeoff;
    @OneToMany(mappedBy = "equipment")
    @ToString.Exclude
    private List<RepairHistory> historyList;
}
