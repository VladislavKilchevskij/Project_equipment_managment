package com.example.stuffmanagerapp.dto.employee;

import com.example.stuffmanagerapp.dto.DepartmentShortDto;
import lombok.Data;

@Data
public class EmployeeDto {
    private Integer id;
    private String surname;
    private String firstName;
    private String patronymic;
    private String position;
    private boolean isChief = false;
    private DepartmentShortDto department;
}
