package com.example.stuffmanagerapp.mapper.employee;

import com.example.stuffmanagerapp.dto.employee.EmployeeDto;
import com.example.stuffmanagerapp.mapper.department.DepartmentMapper;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.model.entity.Employee;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { DepartmentMapper.class })
public interface EmployeeMapper extends DefaultMapper<EmployeeDto, Employee> {
}
