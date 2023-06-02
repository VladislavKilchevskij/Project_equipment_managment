package com.example.stuffmanagerapp.dto;

import com.example.stuffmanagerapp.dto.employee.EmployeeChiefDto;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentDto {
    private Integer id;
    private String departmentName;
    private Integer floor;
    private String phoneNumber;
    private List<EmployeeChiefDto> employees;
}
