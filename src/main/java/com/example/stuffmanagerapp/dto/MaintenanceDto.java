package com.example.stuffmanagerapp.dto;

import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class MaintenanceDto {
    private Integer id;
    private BigDecimal totalServiceCost;
    private Date serviceDate;
    private EmployeeShortDto employee;
}
