package com.example.stuffmanagerapp.dto;

import com.example.stuffmanagerapp.dto.equipment.EquipmentShortDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepairHistoryDto {
    private Integer id;
    private String malfunction;
    private String renderedService;
    private BigDecimal serviceCoast;
    private EquipmentShortDto equipment;
}
