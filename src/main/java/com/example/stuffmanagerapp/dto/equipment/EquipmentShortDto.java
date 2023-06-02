package com.example.stuffmanagerapp.dto.equipment;

import lombok.Data;

import java.sql.Date;

@Data
public class EquipmentShortDto {
    private Integer id;
    private String category;
    private String equipmentName;
    private Date nextInspectionDate;
    private Date usageEndDate;
    private String message;
}
