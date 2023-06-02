package com.example.stuffmanagerapp.mapper.department;

import com.example.stuffmanagerapp.dto.DepartmentDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Department;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {DepartmentMapper.class})
public interface DepartmentListMapper extends DefaultListMapper<DepartmentDto, Department> {
}
