package com.example.stuffmanagerapp.mapper.equipment;

import com.example.stuffmanagerapp.dto.equipment.EquipmentShortDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.model.entity.Equipment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EquipmentShortMapper extends DefaultMapper<EquipmentShortDto, Equipment> {

    @Mapping(target = "message", ignore = true)
    EquipmentShortDto toDto(Equipment equipment);
}
