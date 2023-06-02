package com.example.stuffmanagerapp.dto.writeoff;

import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class WriteoffShortDto {
    private Integer id;
    private Date writeoffDate;
    private BigDecimal equipmentTotalCost;
    private EmployeeShortDto employee;
}
