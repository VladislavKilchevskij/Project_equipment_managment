package com.example.stuffmanagerapp.mapper.repairHistory;

import com.example.stuffmanagerapp.dto.RepairHistoryDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultMapper;
import com.example.stuffmanagerapp.mapper.equipment.EquipmentShortMapper;
import com.example.stuffmanagerapp.model.entity.RepairHistory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { EquipmentShortMapper.class })
public interface RepairMapper extends DefaultMapper<RepairHistoryDto, RepairHistory> {

}
