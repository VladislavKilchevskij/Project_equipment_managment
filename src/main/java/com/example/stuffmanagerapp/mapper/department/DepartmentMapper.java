package com.example.stuffmanagerapp.mapper.department;

import com.example.stuffmanagerapp.dto.DepartmentDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeChiefListMapper;
import com.example.stuffmanagerapp.model.entity.Department;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EmployeeChiefListMapper.class})
public interface DepartmentMapper extends DefaultMapper<DepartmentDto, Department> {
}
