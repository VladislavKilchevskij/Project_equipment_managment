package com.example.stuffmanagerapp.mapper.writeoff;

import com.example.stuffmanagerapp.dto.writeoff.WriteoffDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.mapper.employee.EmployeeShortMapper;
import com.example.stuffmanagerapp.mapper.equipment.EquipmentListMapper;
import com.example.stuffmanagerapp.model.entity.Writeoff;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { EmployeeShortMapper.class })
public interface WriteoffShortMapper extends DefaultMapper<WriteoffDto, Writeoff> {
}
