package com.example.stuffmanagerapp.mapper.employee;

import com.example.stuffmanagerapp.dto.employee.EmployeeChiefDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Employee;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EmployeeChiefMapper.class})
public interface EmployeeChiefListMapper extends DefaultListMapper<EmployeeChiefDto, Employee> {
}
