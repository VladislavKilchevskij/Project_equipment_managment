package com.example.stuffmanagerapp.service;

import com.example.stuffmanagerapp.dto.employee.EmployeeDto;
import com.example.stuffmanagerapp.dto.employee.EmployeeGetEquipmentDto;
import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;

import java.util.List;

public interface EmployeeService {
    void save(EmployeeDto employeeDto);
    void save(EmployeeGetEquipmentDto employeeDto);

    List<EmployeeDto> getAll();
    EmployeeDto getById(Integer id);

    EmployeeShortDto getShortById(Integer id);

    List<EmployeeDto> search(String columnName, String request);

    void update(EmployeeDto employeeDto);
    void delete(Integer id);
}
