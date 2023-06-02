package com.example.stuffmanagerapp.mapper.maintenance;

import com.example.stuffmanagerapp.dto.MaintenanceDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeShortMapper;
import com.example.stuffmanagerapp.model.entity.Maintenance;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { EmployeeShortMapper.class })
public interface MaintenanceMapper extends DefaultMapper<MaintenanceDto, Maintenance> {
}
