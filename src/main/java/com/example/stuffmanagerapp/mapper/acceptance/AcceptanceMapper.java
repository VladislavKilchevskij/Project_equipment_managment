package com.example.stuffmanagerapp.mapper.acceptance;

import com.example.stuffmanagerapp.dto.AcceptanceDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeShortMapper;
import com.example.stuffmanagerapp.mapper.equipment.EquipmentListMapper;
import com.example.stuffmanagerapp.model.entity.Acceptance;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { EquipmentListMapper.class, EmployeeShortMapper.class })
public interface AcceptanceMapper extends DefaultMapper<AcceptanceDto, Acceptance> {

    @Mapping(target = "equipmentRowAmount", ignore = true)
    AcceptanceDto toDto(Acceptance acceptance);
}
