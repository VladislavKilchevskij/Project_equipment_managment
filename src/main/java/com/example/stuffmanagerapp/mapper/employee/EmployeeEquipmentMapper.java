package com.example.stuffmanagerapp.mapper.employee;

import com.example.stuffmanagerapp.dto.employee.EmployeeGetEquipmentDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.mapper.equipment.EquipmentListMapper;
import com.example.stuffmanagerapp.model.entity.Employee;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { EquipmentListMapper.class })
public interface EmployeeEquipmentMapper extends DefaultMapper<EmployeeGetEquipmentDto, Employee> {
}
