package com.example.stuffmanagerapp.dto.employee;

import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeGetEquipmentDto {
    private Integer id;
    private String surname;
    private String firstName;
    private String patronymic;
    private String position;
    private List<EquipmentDto> equipment;
}
