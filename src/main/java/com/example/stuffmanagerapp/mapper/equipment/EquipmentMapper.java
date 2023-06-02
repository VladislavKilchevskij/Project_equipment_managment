package com.example.stuffmanagerapp.mapper.equipment;

import com.example.stuffmanagerapp.dto.equipment.EquipmentDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.model.entity.Equipment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EquipmentMapper extends DefaultMapper<EquipmentDto, Equipment> {

    @Mapping(target = "amount", ignore = true)
    EquipmentDto toDto(Equipment equipment);
}
