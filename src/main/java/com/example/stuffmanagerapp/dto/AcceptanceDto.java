package com.example.stuffmanagerapp.dto;

import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.model.entity.Employee;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class AcceptanceDto {
    private Integer id;
    private Date acceptanceDate;
    private String provider;
    private BigDecimal totalCoast;
    private List<EquipmentDto> equipment = new ArrayList<>();
    private EmployeeShortDto employee;
    private Integer equipmentRowAmount;
}
