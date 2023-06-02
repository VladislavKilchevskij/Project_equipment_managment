package com.example.stuffmanagerapp.service;


import com.example.stuffmanagerapp.dto.DepartmentDto;
import com.example.stuffmanagerapp.dto.DepartmentShortDto;

import java.util.List;

public interface DepartmentService {
    void save(DepartmentShortDto departmentShortDto);
    List<DepartmentShortDto> getAll();
    List<DepartmentDto> getAllWithChief();
    DepartmentShortDto getById(Integer id);

    List<DepartmentDto> search(String columnName, String request);

    void update(DepartmentShortDto departmentShortDto);
    boolean delete(Integer id);
}
