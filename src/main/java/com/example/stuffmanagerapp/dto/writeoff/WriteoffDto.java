package com.example.stuffmanagerapp.dto.writeoff;

import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class WriteoffDto {
    private Integer id;
    private Date writeoffDate;
    private BigDecimal equipmentTotalCost;
    private List<EquipmentDto> equipment = new ArrayList<>();
    private EmployeeShortDto employee;
}
