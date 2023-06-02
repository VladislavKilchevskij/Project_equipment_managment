package com.example.stuffmanagerapp.mapper.equipment;

import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Equipment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { EquipmentMapper.class })
public interface EquipmentListMapper extends DefaultListMapper<EquipmentDto, Equipment> {
}
