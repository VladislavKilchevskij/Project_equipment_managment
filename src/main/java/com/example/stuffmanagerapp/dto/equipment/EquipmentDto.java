package com.example.stuffmanagerapp.dto.equipment;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class EquipmentDto {
    private Integer id;
    private String category;
    private String equipmentName;
    private Integer inspectionTerm;
    private Integer usageTerm;
    private Date nextInspectionDate;
    private Date usageEndDate;
    private String status;
    private BigDecimal coast;
    private Integer amount;
    private String writeoffReason;
}
