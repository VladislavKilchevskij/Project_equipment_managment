package com.example.stuffmanagerapp.mapper.request;

import com.example.stuffmanagerapp.dto.RequestDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeShortMapper;
import com.example.stuffmanagerapp.mapper.maintenance.MaintenanceMapper;
import com.example.stuffmanagerapp.mapper.repairHistory.RepairListMapper;
import com.example.stuffmanagerapp.model.entity.Request;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { RepairListMapper.class, EmployeeShortMapper.class, MaintenanceMapper.class})
public interface RequestMapper extends DefaultMapper<RequestDto, Request> {
    @Mapping(target = "rowAmount", ignore = true)
    RequestDto toDto(Request entity);
}
