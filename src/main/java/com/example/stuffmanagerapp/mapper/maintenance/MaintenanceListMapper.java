package com.example.stuffmanagerapp.mapper.maintenance;

import com.example.stuffmanagerapp.dto.MaintenanceDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Maintenance;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { MaintenanceMapper.class })
public interface MaintenanceListMapper extends DefaultListMapper<MaintenanceDto, Maintenance> {
}
