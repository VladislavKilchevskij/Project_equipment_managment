package com.example.stuffmanagerapp.dto.equipment;

import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import com.example.stuffmanagerapp.dto.writeoff.WriteoffShortDto;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class EquipmentTableDto {
    private Integer id;
    private String category;
    private String equipmentName;
    private Date nextInspectionDate;
    private Date usageEndDate;
    private String status;
    private BigDecimal coast;
    private EmployeeShortDto employee;
    private WriteoffShortDto writeoff;
    private String writeoffReason;
}
