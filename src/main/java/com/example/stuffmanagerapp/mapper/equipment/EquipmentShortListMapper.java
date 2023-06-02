package com.example.stuffmanagerapp.mapper.equipment;

import com.example.stuffmanagerapp.dto.equipment.EquipmentShortDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Equipment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { EquipmentShortMapper.class })
public interface EquipmentShortListMapper extends DefaultListMapper<EquipmentShortDto, Equipment> {
}
