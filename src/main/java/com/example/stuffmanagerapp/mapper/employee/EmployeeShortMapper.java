package com.example.stuffmanagerapp.mapper.employee;

import com.example.stuffmanagerapp.dto.employee.EmployeeShortDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.model.entity.Employee;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmployeeShortMapper extends DefaultMapper<EmployeeShortDto, Employee> {
}
