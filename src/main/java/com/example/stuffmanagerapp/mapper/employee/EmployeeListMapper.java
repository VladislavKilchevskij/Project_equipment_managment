package com.example.stuffmanagerapp.mapper.employee;

import com.example.stuffmanagerapp.dto.employee.EmployeeDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Employee;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EmployeeMapper.class})
public interface EmployeeListMapper extends DefaultListMapper<EmployeeDto, Employee> {
}
